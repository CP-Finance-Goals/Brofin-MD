package com.example.brofin.presentation.main.home

import ListTransactions
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.utils.toIndonesianCurrency


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


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        BudgetHeader(
            balance = userBalance?.toIndonesianCurrency() ?: "Rp. 0",
            income = totalIncome?.toIndonesianCurrency() ?: "Rp. 0",
            outcome = totalExpenses?.toIndonesianCurrency() ?: "Rp. 0",
            savings = "Rp. 0"
        )

        Spacer(Modifier.height(16.dp))

        ListTransactions(budgetList = diaries)
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
            text = "Uang anda saat ini yang tersisa:",
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

@Composable
fun BudgetItem(label: String, amount: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .background(color = color, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .width(100.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        Text(
            text = amount,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}