package com.example.brofin.presentation.main.budget.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brofin.utils.CategoryExpenses
import com.example.brofin.utils.Expense


@Composable
fun BottomSheetContent() {
    var sliderPosition by remember { mutableStateOf(0f) }

    val kategoriList = Expense.categoryExpensesLists
    var selectedKategori by remember { mutableStateOf<CategoryExpenses?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Slider
            Text(
                text = "${sliderPosition.toInt()}% dari total uang yang tersisa",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Slider
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        KategoriPengeluaranDropdown(
            kategoriList = kategoriList,
            onKategoriSelected = { kategori ->
                selectedKategori = kategori
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReminderButtonWithIcon(isReminderActive = false) {

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Todo: Implementasi aksi simpan
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}