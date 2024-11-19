package com.example.brofin.presentation.temp.budget.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brofin.data.mapper.toBudgetingDiary
import com.example.brofin.presentation.temp.budget.diary.components.DatePickerButton
import com.example.brofin.presentation.temp.budget.diary.components.ListBudgeting
import kotlinx.coroutines.launch

@Composable
fun BudgetingDiaryScreen(viewModel: BudgetingDiaryViewModel = hiltViewModel()) {

    var showFilterSheet by remember { mutableStateOf(false) }
    var isExpense by remember { mutableStateOf<Boolean?>(null) }
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }
    var minAmount by remember { mutableStateOf<Double?>(null) }
    var maxAmount by remember { mutableStateOf<Double?>(null) }

    var tempIsExpense by remember { mutableStateOf<Boolean?>(null) }
    var tempStartDate by remember { mutableStateOf<Long?>(null) }
    var tempEndDate by remember { mutableStateOf<Long?>(null) }
    var tempMinAmount by remember { mutableStateOf("") }
    var tempMaxAmount by remember { mutableStateOf("") }

    // Fungsi untuk mereset semua filter ke nilai awal
    fun resetFilters() {
        isExpense = null
        startDate = null
        endDate = null
        minAmount = null
        maxAmount = null
        tempIsExpense = null
        tempStartDate = null
        tempEndDate = null
        tempMinAmount = ""
        tempMaxAmount = ""
    }
    var isButtonActive by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CustomButton(
                text = "Custom Button",
                isActive = isButtonActive,
                onClick = {
                    isButtonActive = !isButtonActive // Toggle active state
                }
            )
//            Button(onClick = { viewModel.sampleInsert() }) {
//                Text(text = "Sample Insert")
//            }

            Button(onClick = { viewModel.deleteAll() }) {
                Text(text = "Delete All")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                tempIsExpense = isExpense
                tempStartDate = startDate
                tempEndDate = endDate
                tempMinAmount = minAmount?.toString() ?: ""
                tempMaxAmount = maxAmount?.toString() ?: ""
                showFilterSheet = true
            }) {
                Text(text = if (isExpense != null || startDate != null || endDate != null || minAmount != null || maxAmount != null) "Filter: ON" else "Filter: OFF")
            }

            Spacer(modifier = Modifier.height(16.dp))


            val filteredDiaries by viewModel.getFilteredDiaries(
                startDate = startDate,
                endDate = endDate,
                isExpense = isExpense,
                minAmount = minAmount,
                maxAmount = maxAmount
            ).collectAsState(initial = emptyList())

            val data = filteredDiaries.map { it?.toBudgetingDiary() }

            ListBudgeting(
                modifier = Modifier.fillMaxSize(),
                listBudgeting = data
            )
        }

        // Modal Bottom shiiit
        if (showFilterSheet) {
            FilterBottomSheet(
                initialIsExpense = tempIsExpense,
                onIsExpenseChange = { tempIsExpense = it },
                startDate = tempStartDate,
                endDate = tempEndDate,
                onDateChange = { selectedStartDate, selectedEndDate ->
                    tempStartDate = selectedStartDate
                    tempEndDate = selectedEndDate
                },
                minAmount = tempMinAmount.toDoubleOrNull(),
                onMinAmountChange = { tempMinAmount = it?.toString() ?: "" },
                maxAmount = tempMaxAmount.toDoubleOrNull(),
                onMaxAmountChange = { tempMaxAmount = it?.toString() ?: "" },
                onDismiss = { showFilterSheet = false },
                onApply = {
                    isExpense = tempIsExpense
                    startDate = tempStartDate
                    endDate = tempEndDate
                    minAmount = tempMinAmount.toDoubleOrNull()
                    maxAmount = tempMaxAmount.toDoubleOrNull()
                    showFilterSheet = false
                },
                onReset = {
                    resetFilters()
                    showFilterSheet = false
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    initialIsExpense: Boolean?,
    onIsExpenseChange: (Boolean?) -> Unit,
    startDate: Long?,
    endDate: Long?,
    onDateChange: (Long?, Long?) -> Unit,
    minAmount: Double?,
    onMinAmountChange: (Double?) -> Unit,
    maxAmount: Double?,
    onMaxAmountChange: (Double?) -> Unit,
    onDismiss: () -> Unit,
    onApply: () -> Unit,
    onReset: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var localIsExpense by remember { mutableStateOf(initialIsExpense) }
    var localStartDate by remember { mutableStateOf(startDate) }
    var localEndDate by remember { mutableStateOf(endDate) }
    var localMinAmount by remember { mutableStateOf(minAmount?.toString() ?: "") }
    var localMaxAmount by remember { mutableStateOf(maxAmount?.toString() ?: "") }


    ModalBottomSheet(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Filter", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Tombol Reset
                Button(
                    onClick = {
                        onReset()
                        coroutineScope.launch { onDismiss() }
                    }
                ) {
                    Text("Reset")
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Tombol Terapkan
                Button(
                    onClick = {
                        onIsExpenseChange(localIsExpense)
                        onDateChange(localStartDate, localEndDate)
                        onMinAmountChange(localMinAmount.toDoubleOrNull())
                        onMaxAmountChange(localMaxAmount.toDoubleOrNull())
                        coroutineScope.launch {
                            onApply()
                            onDismiss()
                        }
                    }
                ) {
                    Text("Terapkan")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Jenis Transaksi")
            Row {
                Button(onClick = { localIsExpense = null }) {
                    Text(text = "Semua")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { localIsExpense = false }) {
                    Text(text = "Pemasukan")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { localIsExpense = true }) {
                    Text(text = "Pengeluaran")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Rentang Tanggal")
            DatePickerButton(
                date = localStartDate,
                label = "Tanggal Mulai",
                onDateSelected = { localStartDate = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            DatePickerButton(
                date = localEndDate,
                label = "Tanggal Akhir",
                onDateSelected = { localEndDate = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Nilai Transaksi")
            OutlinedTextField(
                value = localMinAmount,
                onValueChange = { localMinAmount = it },
                label = { Text("Nilai Minimum") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = localMaxAmount,
                onValueChange = { localMaxAmount = it },
                label = { Text("Nilai Maksimum") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun CustomButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    // Define colors for active and inactive states
    val backgroundColor = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f)
    val contentColor = if (isActive) MaterialTheme.colorScheme.onPrimary else Color.White.copy(alpha = 0.6f)
    val textColor = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = text,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
        )
    }
}

