package com.example.brofin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.brofin.presentation.main.budget.BudgetDetailScreen
import com.example.brofin.presentation.navigation.Navigation
import com.example.brofin.ui.theme.BrofinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge(
                statusBarStyle = if (isSystemInDarkTheme()) {
                    SystemBarStyle.dark(
                        scrim = Color(0xFF1A202C).toArgb(),
                    )
                } else {
                    SystemBarStyle.light(
                        scrim = Color(0xFFF7FAFC).toArgb(),
                        darkScrim = Color(0xFF1A202C).toArgb()
                    )
                },
            )

            BrofinTheme {
                BudgetDetailScreen(
                    onBackClick = { /* Handle Back */ },
                    onDeleteClick = { /* Handle Delete */ },
                    onEditClick = { /* Handle Edit */ },
                    categoryName = "Shopping",
                    remainingAmount = "$0",
                    totalSpent = "$1200",
                    budgetLimit = "$1000",
                    isOverLimit = true,
                    progressColor = Color(0xFFFFA726), // Warna oranye untuk progress
                    progress = 1f // 100% dari limit tercapai
                )
            }
        }
    }
}
