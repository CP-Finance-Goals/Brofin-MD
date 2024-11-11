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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.brofin.domain.models.BudgetingDiary

fun generateDummyTransactions(): List<BudgetingDiary> {
    return listOf(
        BudgetingDiary(
            id = 1,
            userId = "user_1",
            date = System.currentTimeMillis(),
            description = "Makan Siang",
            amount = 50_000.0,
            isExpense = true
        ),
        BudgetingDiary(
            id = 2,
            userId = "user_1",
            date = System.currentTimeMillis() - 86_400_000, // 1 hari yang lalu
            description = "Gaji Mingguan",
            amount = 500_000.0,
            isExpense = false
        )
    )
}


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val transactions = generateDummyTransactions()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        BudgetHeader(
            balance = "Rp. 50.000,-",
            income = "Rp. 100.000",
            outcome = "Rp. 50.000",
            savings = "Rp. 0"
        )

        Spacer(Modifier.height(16.dp))

        ListTransactions(transactions)
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
            text = "Budget anda saat ini:",
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
            BudgetItem("Tabungan", savings, Color(0xFF455A67))
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
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}