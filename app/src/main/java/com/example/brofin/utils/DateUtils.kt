package com.example.brofin.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    /**
     * Converts a Long timestamp to a formatted date string.
     *
     * @param timestamp the timestamp in milliseconds.
     * @param pattern the date format pattern (e.g., "dd-MM-yyyy" or "yyyy-MM-dd HH:mm").
     *                Defaults to "dd-MM-yyyy".
     * @return a formatted date string.
     */

    fun formatDate(timestamp: Long, pattern: String = "dd-MM-yyyy"): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(date)
    }
}
