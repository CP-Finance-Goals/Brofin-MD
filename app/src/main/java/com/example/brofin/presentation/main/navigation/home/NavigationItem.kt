package com.example.brofin.presentation.main.navigation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector) {
    data object Home : Screen("Home", Icons.Default.Home)
    data object Profile : Screen("Budget", Icons.Default.MonetizationOn)
    data object Financials : Screen("Finansial", Icons.Default.Money)
    data object Settings : Screen("Pengaturan", Icons.Default.Settings)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Profile,
    Screen.Financials,
    Screen.Settings
)
