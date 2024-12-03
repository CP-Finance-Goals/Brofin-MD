package com.example.brofin.presentation.camera

import android.net.Uri
import android.util.Log
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
   private val  cameraRepository: CameraRepository
): ViewModel() {

    private val _cameraState = MutableStateFlow<StateApp<Boolean>>(StateApp.Idle)
    val cameraState = _cameraState.asStateFlow()

    fun onTakePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Uri?) -> Unit,
    ) {
        _cameraState.value = StateApp.Loading
        viewModelScope.launch {
            try {
                val uri = cameraRepository.takePhoto(controller)
                onPhotoTaken(uri)
                _cameraState.value = StateApp.Success(true)
            } catch (e: Exception) {
                Log.e(TAG, "Error when take a photo: $e")
                _cameraState.value = StateApp.Error("Gagal mengambil foto")
            }
        }
    }

    companion object{
        const val TAG = "CameraViewmodel"
    }
}