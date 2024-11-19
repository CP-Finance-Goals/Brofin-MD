package com.example.brofin.presentation.temp.budget.diary.components

import android.app.DatePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DatePickerButton(
    date: Long?,
    label: String,
    onDateSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()

    date?.let { calendar.timeInMillis = it }

    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val dateText = date?.let { dateFormatter.format(Date(it)) } ?: "Pilih Tanggal"

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Button(onClick = {
        datePickerDialog.show()
    }) {
        Text(text = "$label: $dateText")
    }
}