package com.example.brofin.presentation.main.home

import ListTransactions
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.presentation.main.home.components.BudgetItem
import com.example.brofin.presentation.main.home.components.BudgetingSheetContent
import com.example.brofin.presentation.main.home.components.WarningCard
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import com.example.brofin.utils.toIndonesianCurrency
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewmodel: HomeViewModel = hiltViewModel()
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val diaries by viewmodel.budgetingDiaries.collectAsStateWithLifecycle(emptyList())
    val totalIncome by viewmodel.totalIncome.collectAsStateWithLifecycle(0.0)
    val totalExpenses by viewmodel.totalExpenses.collectAsStateWithLifecycle(0.0)
    val userBalance by viewmodel.userBalance.collectAsStateWithLifecycle(0.0)
    val savings by viewmodel.totalSavings.collectAsStateWithLifecycle(0.0)
    var isSheetOpen by remember { mutableStateOf(false) }

    val budgetingIsExist by viewmodel.budgetingUserIsExist.collectAsStateWithLifecycle(false)

    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        HomeHeader(
            showWarningHeader = !budgetingIsExist,
            onSetBudgetClick = {
                coroutineScope.launch { sheetState.show() }
                isSheetOpen = true
            },
            onAddIncomeClick = { /*TODO*/ },
            balance = userBalance,
            income = totalIncome,
            outcome = totalExpenses,
            savings = savings
        )

        Spacer(Modifier.height(16.dp))

        ListTransactions(budgetList = diaries)

        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    coroutineScope.launch { sheetState.hide() }
                    isSheetOpen = false
                }
            ) {
                BudgetingSheetContent(
                    onSaveBudget = {  budget ->
                        coroutineScope.launch { sheetState.hide() }
                        isSheetOpen = false
                        viewmodel.insertUserBalance(
                            userBalance = UserBalance(
                                balance = budget,
                                currentBalance = budget * 0.8,
                                monthAndYear = getCurrentMonthAndYearAsLong(),
                                userId = "",
                                savings = 0.0
                            )
                        )
                    },
                    onCancel = {
                        coroutineScope.launch { sheetState.hide() }
                        isSheetOpen = false
                    }
                )
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    showDialog: Boolean
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onCancel() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text("Batal")
                }
            },
            title = {
                Text(text = "Konfirmasi Simpan")
            },
            text = {
                Text(text = "Apakah pendapatan ini sudah benar?")
            },
            modifier = modifier
        )
    }
}


@Composable
fun HomeHeader(
    showWarningHeader: Boolean,
    onSetBudgetClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    balance: Double?,
    income: Double?,
    outcome : Double?,
    savings : Double?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (showWarningHeader) {
            WarningCard(
                onSetBudgetClick = onSetBudgetClick,
                onAddIncomeClick = onAddIncomeClick
            )
        } else {
            BudgetHeader(
                balance = balance?.toIndonesianCurrency() ?: "Rp. 0",
                income = income?.toIndonesianCurrency() ?: "Rp. 0",
                outcome = outcome?.toIndonesianCurrency() ?: "Rp. 0",
                savings = savings?.toIndonesianCurrency() ?: "Rp. 0"
            )
        }
    }
}


@Composable
fun BudgetHeader(balance: String, income: String, outcome: String, savings: String) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.primary, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Uang anda bulan ini yang tersisa:",
            style = MaterialTheme.typography.bodyLarge,
            color = colors.onPrimary
        )
        Text(
            text = balance,
            style = MaterialTheme.typography.headlineLarge,
            color = colors.onPrimary
        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            BudgetItem("Pendapatan", income, Color(0xFF66BB6A))
            BudgetItem("Pengeluaran", outcome, Color(0xFFEF5350))
            BudgetItem("Tabungan", savings, Color(0xFF455A80))
        }
    }
}
