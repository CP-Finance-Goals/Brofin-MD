package com.example.brofin.presentation.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brofin.utils.formatStringToIndonesianCurrency
import com.example.brofin.utils.getCurrentMonthAndYearInIndonesian

@Composable
fun SetupIncome(
    setupIncomeViewModel: SetupIncomeViewModel = hiltViewModel(),
) {
    var income by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val currentMonth = getCurrentMonthAndYearInIndonesian()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bulan Saat Ini: $currentMonth",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = income,
                onValueChange = {
                    income = it
                    errorMessage = "" // Hapus pesan error saat input berubah
                },
                label = { Text("Masukkan Pendapatan Bulanan") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val incomeValue = income.toDoubleOrNull()
                    if (incomeValue == null || incomeValue <= 0) {
                        errorMessage = "Masukkan angka valid lebih dari 0."
                        return@Button
                    }
                    isDialogOpen = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Pendapatan")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else if (isSaved) {
                Text(
                    text = "Pendapatan berhasil disimpan untuk bulan $currentMonth!",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // AlertDialog untuk konfirmasi
        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDialogOpen = false },
                confirmButton = {
                    Button(onClick = {
                        val incomeValue = income.toDoubleOrNull()
                        if (incomeValue != null && incomeValue > 0) {
                            isSaved = true
                            setupIncomeViewModel.insertIncome(incomeValue)
                        }
                        isDialogOpen = false
                    }) {
                        Text("Simpan")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { isDialogOpen = false }) {
                        Text("Batal")
                    }
                },
                title = { Text("Konfirmasi Pendapatan") },
                text = {
                    Text(
                        text = "Apakah Anda yakin ingin menyimpan pendapatan sebesar ${formatStringToIndonesianCurrency(income.ifBlank { "0" })} untuk bulan $currentMonth? Pastikan data sudah benar sebelum menyimpan."
                    )
                }
            )
        }
    }
}
