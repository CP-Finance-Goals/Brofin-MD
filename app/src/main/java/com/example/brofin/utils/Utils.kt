package com.example.brofin.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentMonthAndYearInIndonesian(): String {
    val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return formatter.format(Date(System.currentTimeMillis()))
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

