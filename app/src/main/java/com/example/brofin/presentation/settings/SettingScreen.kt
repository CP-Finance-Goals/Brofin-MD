package com.example.brofin.presentation.settings

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brofin.data.local.datastore.utils.UserPreferences
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.brofin.presentation.permission.PermissionHandler


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userPreferences by settingViewModel.userPreferencesFlow.collectAsState(initial = UserPreferences())
    val isDarkModeEnabled = userPreferences.isDarkMode == true
    val isNotificationEnabled = userPreferences.budgetNotificationEnabled == true

    // State untuk mengelola dialog izin
    var showPermissionHandler by remember { mutableStateOf(false) }

    // Permission state untuk notifikasi
    val notificationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                android.Manifest.permission.POST_NOTIFICATIONS
            } else {
                ""
            }
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bagian atas
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Pengaturan",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Switch Dark Mode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isDarkModeEnabled) "Tema: Dark Mode" else "Tema: Light Mode",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isDarkModeEnabled,
                        onCheckedChange = { isDarkMode ->
                            settingViewModel.updateDarkMode(isDarkMode)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isNotificationEnabled) "Notifikasi: Aktif" else "Notifikasi: Nonaktif",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isNotificationEnabled,
                        onCheckedChange = { isEnabled ->
                            if (isEnabled && !notificationPermissionState.allPermissionsGranted) {
                                showPermissionHandler = true
                            } else {
                                settingViewModel.updateNotification(isEnabled)
                            }
                        }
                    )
                }

                if (showPermissionHandler) {
                    PermissionHandler(
                        multiplePermissionsState = notificationPermissionState,
                        onPermissionGranted = {
                            showPermissionHandler = false
                            settingViewModel.updateNotification(true) // Aktifkan notifikasi
                        },
                        onPermissionDenied = { message ->
                            showPermissionHandler = false
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        },
                        onPermissionRevoked = { message ->
                            showPermissionHandler = false
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        },
                        message = "Aplikasi memerlukan izin untuk mengaktifkan notifikasi."
                    )
                }
            }

            Button(
                onClick = {
                    settingViewModel.logout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Logout")
            }
        }
    }
}
