package com.example.brofin.presentation.main.home.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.brofin.presentation.main.home.ConfirmationDialog


@Composable
fun BudgetingSheetContent(
    onSaveBudget: (Double) -> Unit,
    onCancel: () -> Unit
) {
    var pendapatanMonthe by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var pendapatanConfirm by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Atur Budgeting",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CustomTextFieldTwo(
            label = "Pendapatan",
            text = pendapatanMonthe,
            onTextChange = { pendapatanMonthe = it },
            validate = {
                when {
                    it.isEmpty() -> "Pendapatan tidak boleh kosong"
                    it.toDoubleOrNull() == null -> "Pendapatan harus berupa angka"
                    it.toDouble() < 0 -> "Pendapatan tidak boleh kurang dari 0"
                    else -> ""
                }

            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onCancel) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                       when {
                           pendapatanMonthe.isEmpty() -> Toast.makeText(context, "Pendapatan tidak boleh kosong", Toast.LENGTH_LONG).show()
                           pendapatanMonthe.toDoubleOrNull() == null -> Toast.makeText(context, "Pendapatan harus berupa angka", Toast.LENGTH_LONG).show()
                           pendapatanMonthe.toDouble() < 0 -> Toast.makeText(context, "Pendapatan tidak boleh kurang dari 0", Toast.LENGTH_LONG).show()
                           else -> {
                                 showDialog = true
                                    pendapatanConfirm = pendapatanMonthe
                           }
                       }

                }
            ) {
                Text("Simpan", style = MaterialTheme.typography.bodyMedium)
            }

        }
        ConfirmationDialog(
            showDialog = showDialog,
            onCancel = {
                showDialog = false
            },
            onConfirm = {
                onSaveBudget(pendapatanMonthe.toDouble())
                showDialog = false
            },
            amount = pendapatanConfirm.toDouble()
        )
    }
}
