package com.example.brofin.data.repository

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.brofin.R
import com.example.brofin.domain.repository.CameraRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resumeWithException


class CameraRepositoryImpl @Inject constructor(
    private val application: Application
) : CameraRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun takePhoto(controller: LifecycleCameraController): Uri {
        return suspendCancellableCoroutine { continuation ->
            controller.takePicture(
                ContextCompat.getMainExecutor(application),
                object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            val scope = CoroutineScope(Dispatchers.IO)
                            scope.launch {
                                try {
                                    val uri = processAndSavePhoto(image)
                                    continuation.resume(uri) { cause->
                                        onCancellation(cause)
                                    }
                                } catch (e: Exception) {
                                    continuation.resumeWithException(e)
                                } finally {
                                    image.close()
                                }
                            }
                        }

                    override fun onError(exception: ImageCaptureException) {
                        continuation.resumeWithException(exception)
                    }
                }
            )


            continuation.invokeOnCancellation {
                Log.d("CameraRepositoryImpl", "takePhoto: Cancelled")
            }
        }
    }

    private fun onCancellation(cause: Throwable) {
        Log.d("CameraRepositoryImpl", "Camera capture was cancelled: ${cause.message}")
    }

    private suspend fun processAndSavePhoto(image: ImageProxy): Uri {
        // Konversi ImageProxy ke Bitmap dan lakukan rotasi
        val bitmap = withContext(Dispatchers.IO) {
            val matrix = android.graphics.Matrix().apply {
                postRotate(image.imageInfo.rotationDegrees.toFloat())
            }
            Bitmap.createBitmap(
                image.toBitmap(),
                0, 0,
                image.width, image.height,
                matrix, true
            )
        }

        return savePhoto(bitmap) ?: throw Exception("Failed to save photo")
    }

    private suspend fun savePhoto(bitmap: Bitmap): Uri? {
        return withContext(Dispatchers.IO) {
            val resolver: ContentResolver = application.contentResolver
            val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val appName = application.getString(R.string.app_name)
            val timeInMillis = System.currentTimeMillis()
            val imageContentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "${timeInMillis}_image.jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/$appName")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.DATE_TAKEN, timeInMillis)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            val imageUri: Uri? = resolver.insert(imageCollection, imageContentValues)
            imageUri?.let { uri ->
                try {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                    imageContentValues.clear()
                    imageContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, imageContentValues, null, null)
                    uri
                } catch (e: Exception) {
                    resolver.delete(uri, null, null)
                    Log.e("CameraRepositoryImpl", "Error when save photo")
                    null
                }
            }
        }
    }
}
