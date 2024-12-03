package com.example.brofin.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    showDialog: Boolean,
    message: String,
    onDismissRequest: () -> Unit
) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = "Error", style = MaterialTheme.typography.headlineMedium)
            },
            text = {
                Text(text = message, style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton = {
                Button(
                    onClick = onDismissRequest
                ) {
                    Text(text = "OK", style = MaterialTheme.typography.bodyMedium)
                }
            }
        )
    }
    
}