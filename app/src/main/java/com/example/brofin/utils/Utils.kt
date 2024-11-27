package com.example.brofin.utils

import android.os.Build
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
fun getFormattedTimeInMillis(currentTimeMillis: Long): Long {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val zonedDateTime = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(currentTimeMillis),
            ZoneId.of("Asia/Jakarta") // Waktu Indonesia Barat (WIB)
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

//fun decodeMonthAndYearFromLong(value: Long): String {
//    val inputFormat = SimpleDateFormat("yyyyMM", Locale.getDefault())
//    val outputFormat = SimpleDateFormat("MMMM yyyy", Locale("id", "ID"))
//
//    val date = inputFormat.parse(value.toString())
//    return outputFormat.format(date!!)
//}

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

