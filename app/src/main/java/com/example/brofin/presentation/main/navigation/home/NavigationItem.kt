package com.example.brofin.presentation.main.navigation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector) {
    data object Home : Screen("Home", Icons.Default.Home)
    data object Profile : Screen("Profile", Icons.Default.Person)
    data object Settings : Screen("Settings", Icons.Default.Settings)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Profile,
    Screen.Settings
)
