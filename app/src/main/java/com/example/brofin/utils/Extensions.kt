package com.example.brofin.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Double.toIndonesianCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return format.format(this)
}

fun Long.toFormattedDate(pattern: String = "dd-MM-yyyy"): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(date)
}