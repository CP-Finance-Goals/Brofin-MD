package com.example.brofin.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
                Text(text = "Error")
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                Button(
                    onClick = onDismissRequest
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
    
}