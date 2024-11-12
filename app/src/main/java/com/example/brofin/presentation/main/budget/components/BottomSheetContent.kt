package com.example.brofin.presentation.main.budget.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.brofin.utils.CategoryExpenses
import com.example.brofin.utils.Expense


@Composable
fun BottomSheetContent() {
    val kategoriList = Expense.categoryExpensesLists
    var selectedKategori by remember { mutableStateOf<CategoryExpenses?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PriorityButton(
                percentage = 30,
                isSelected = false,
                onClick = { }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PriorityButton(
                percentage = 50,
                isSelected = false,
                onClick = {  },
                buttonIsActive = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            PriorityButton(
                percentage = 20,
                isSelected = true,
                onClick = { }
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