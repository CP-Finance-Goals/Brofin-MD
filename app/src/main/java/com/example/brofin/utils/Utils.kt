package com.example.brofin.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

fun getCurrentMonthAndYearInIndonesian(): String {
    val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return formatter.format(Date(System.currentTimeMillis()))
}

fun resizeImage(photoUri: Uri, contentResolver: ContentResolver, context: Context, maxWidth: Int, maxHeight: Int): File? {
    try {
        val inputStream = contentResolver.openInputStream(photoUri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
        val newWidth = if (originalBitmap.width > originalBitmap.height) maxWidth else (maxHeight * aspectRatio).toInt()
        val newHeight = if (originalBitmap.height > originalBitmap.width) maxHeight else (newWidth / aspectRatio).toInt()

        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false)

        val resizedFile = File(context.cacheDir, "resized_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(resizedFile)
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.flush()
        outputStream.close()

        return resizedFile
    } catch (e: Exception) {
        Log.e("Image Resize", "Error resizing image", e)
    }
    return null
}

fun preparePhotoPart(photoUri: Uri?, contentResolver: ContentResolver, context: Context): MultipartBody.Part? {
    return if (photoUri != null) {
        try {
            val resizedFile = resizeImage(photoUri, contentResolver, context, maxWidth = 800, maxHeight = 800) // Sesuaikan ukuran max jika perlu

            resizedFile?.let { file ->
                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", file.name, requestBody)
            }
        } catch (e: Exception) {
            Log.e("File Error", "Failed to process the image", e)
            null
        }
    } else {
        null
    }
}

fun getFormattedTimeInMillis(currentTimeMillis: Long): Long {

    if (currentTimeMillis == 0L) {
        return 0L
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val zonedDateTime = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(currentTimeMillis),
            ZoneId.of("Asia/Jakarta")
        )
        zonedDateTime.toInstant().toEpochMilli()
    } else {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))
        calendar.timeInMillis = currentTimeMillis
        calendar.timeInMillis
    }
}

fun Double.toIndonesianCurrency2(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    val formattedAmount = numberFormat.format(this)

    return formattedAmount.replace("Rp", "Rp ").replace("IDR", "").replace(",00", ",-")
}

fun getCurrentMonthAndYearAsLong(): Long {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyyMM", Locale("id", "ID")) // Format untuk Long
    return dateFormat.format(calendar.time).toLong()
}

fun getCurrentMonthAndYearAsLong(date: Long): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date
    val dateFormat = SimpleDateFormat("yyyyMM", Locale("id", "ID")) // Format untuk Long
    return dateFormat.format(calendar.time).toLong()
}

fun decodeMonthAndYearFromLong(date: Long): String {
    val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return formatter.format(Date(date))
}


fun formatToIndonesianCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return format.format(amount)
}

fun formatStringToIndonesianCurrency(amount: String): String {
    return try {
        val value = amount.toDoubleOrNull() ?: 0.0
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        format.format(value)
    } catch (e: Exception) {
        "Invalid amount"
    }
}

