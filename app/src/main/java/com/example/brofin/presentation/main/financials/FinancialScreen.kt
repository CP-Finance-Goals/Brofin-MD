package com.example.brofin.presentation.main.financials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun FinancialScreen(modifier: Modifier = Modifier) {
    var targetDana by remember { mutableStateOf("") }
    var pendapatan by remember { mutableStateOf("") }
    var pengeluaran by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var daerah by remember { mutableStateOf("") }
    var waktuPrediksi by remember { mutableStateOf("Menghitung...") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Atur Tujuan Finansial Anda",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Formulir Input Tujuan
            OutlinedTextField(
                value = targetDana,
                onValueChange = { targetDana = it },
                label = { Text("Jumlah Target (contoh: 11,000,000)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = pendapatan,
                onValueChange = { pendapatan = it },
                label = { Text("Pendapatan Bulanan") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = pengeluaran,
                onValueChange = { pengeluaran = it },
                label = { Text("Pengeluaran Bulanan") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = kategori,
                onValueChange = { kategori = it },
                label = { Text("Kategori (contoh: Elektronik, Kendaraan)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = daerah,
                onValueChange = { daerah = it },
                label = { Text("Daerah") },
                modifier = Modifier.fillMaxWidth()
            )

            // Tombol Prediksi
            Button(
                onClick = {
                    scope.launch {
                        // Simulasi panggilan ML atau backend untuk mendapatkan prediksi waktu
                        waktuPrediksi = prediksiWaktuTercapai(
                            targetDana.toDoubleOrNull() ?: 0.0,
                            pendapatan.toDoubleOrNull() ?: 0.0,
                            pengeluaran.toDoubleOrNull() ?: 0.0
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Hitung Prediksi")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tampilan Waktu Prediksi
            Text(
                text = "Waktu Prediksi Tercapainya Tujuan: $waktuPrediksi",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// Fungsi simulasi prediksi waktu tercapainya tujuan finansial
fun prediksiWaktuTercapai(targetDana: Double, pendapatan: Double, pengeluaran: Double): String {
    val tabunganBulanan = pendapatan - pengeluaran
    return if (tabunganBulanan > 0) {
        val bulanDiperlukan = targetDana / (tabunganBulanan * 0.3) // 30% dari tabungan bulanan
        "${bulanDiperlukan.toInt()} bulan"
    } else {
        "Pendapatan tidak cukup untuk mencapai tujuan ini"
    }
}
