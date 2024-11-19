package com.example.brofin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.brofin.data.local.datastore.utils.UserPreferences
import com.example.brofin.presentation.expenses.AddExpenses
import com.example.brofin.presentation.navigation.Navigation
import com.example.brofin.presentation.settings.SettingViewModel
import com.example.brofin.ui.theme.BrofinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userPreferences by viewModel.userPreferencesFlow.collectAsState(initial = null)

            val isDarkModeActive = userPreferences?.isDarkMode ?: isSystemInDarkTheme()
            enableEdgeToEdge(
                statusBarStyle = if (isDarkModeActive) {
                    SystemBarStyle.dark(
                        scrim = Color(0xFF1A202C).toArgb(), // Warna scrim untuk Dark Mode
                    )
                } else {
                    SystemBarStyle.light(
                        scrim = Color(0xFFF7FAFC).toArgb(), // Warna scrim untuk Light Mode
                        darkScrim = Color(0xFF1A202C).toArgb() // Opsional untuk scrim gelap
                    )
                },
                navigationBarStyle = if (isDarkModeActive) {
                    SystemBarStyle.dark(
                        scrim = Color(0xFF1A202C).toArgb(), // Warna scrim untuk Dark Mode
                    )
                } else {
                    SystemBarStyle.light(
                        scrim = Color(0xFFF7FAFC).toArgb(), // Warna scrim untuk Light Mode
                        darkScrim = Color(0xFF1A202C).toArgb() // Opsional untuk scrim gelap
                    )
                }
            )

            BrofinTheme(
                darkTheme = isDarkModeActive
            ) {
               Navigation()
            }

        }
    }
}
