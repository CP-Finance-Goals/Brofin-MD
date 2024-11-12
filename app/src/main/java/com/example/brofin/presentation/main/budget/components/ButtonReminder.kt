package com.example.brofin.presentation.main.budget.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ReminderButtonWithIcon(
    isReminderActive: Boolean,
    onToggleReminder: (Boolean) -> Unit
) {
    Button(
        onClick = { onToggleReminder(!isReminderActive) },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isReminderActive) MaterialTheme.colorScheme.primary else Color.Gray,
            contentColor = Color.White
        )
    ) {
        Icon(
            imageVector = if (isReminderActive) Icons.Filled.Notifications else Icons.Filled.NotificationsOff,
            contentDescription = if (isReminderActive) "Reminder Aktif" else "Reminder Tidak Aktif",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (isReminderActive) "Nonaktifkan Reminder" else "Aktifkan Reminder",
            style = MaterialTheme.typography.labelLarge
        )
    }
}


