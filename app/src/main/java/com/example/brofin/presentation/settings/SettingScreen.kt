package com.example.brofin.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.data.local.datastore.utils.UserPreferences
import com.example.brofin.domain.StateApp
import com.example.brofin.presentation.components.LoadingDialog
import com.example.brofin.presentation.detail.components.ImageWithLoadingIndicator
import com.example.brofin.presentation.main.financials.components.OpenTextDialog


@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    goProfile: () -> Unit,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val userPreferences by settingViewModel.userPreferencesFlow.collectAsStateWithLifecycle(initialValue = UserPreferences())
    val isDarkModeEnabled = userPreferences.isDarkMode == true
    var showConfirmationDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    var showLoading by remember {
        mutableStateOf(false)
    }

    val updateTabungan = settingViewModel.updateProfile.collectAsStateWithLifecycle().value

    LaunchedEffect(updateTabungan) {
        when (updateTabungan) {
            is StateApp.Success -> {
                Toast.makeText(context, "Tabungan berhasil diperbaharui", Toast.LENGTH_SHORT).show()
                settingViewModel.resetState()
                showLoading = false
            }
            is StateApp.Error -> {
                Toast.makeText(context, "Gagal memperbaharui tabungan", Toast.LENGTH_SHORT).show()
                settingViewModel.resetState()
                showLoading = false
            }
            is StateApp.Loading -> {
                showLoading = true
            }
            else -> {
                showLoading = false
            }
        }
    }


    var showEditTabunganDialog by remember {
        mutableStateOf(false)
    }
    val userProfileData by settingViewModel.userProfile.collectAsStateWithLifecycle()

    var textFieldValue by remember {
        mutableStateOf(userProfileData?.savings.toString())
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        if (showConfirmationDialog) {
            ExitConfirmationDialog(
                onDismiss = { showConfirmationDialog = false },
                onConfirmExit = {
                    settingViewModel.logout()
                }
            )
        }

        LoadingDialog(showLoading) {
            showLoading = false
            settingViewModel.resetState()
        }

        if (showEditTabunganDialog) {
            OpenTextDialog(
                label = "Tabungan",
                textFieldValue = textFieldValue,
                onTextChange =  { textFieldValue = it },
                onDismiss = {
                    showEditTabunganDialog = false
                }
            ) {
                if (it.isEmpty()) {
                    Toast.makeText(context, "Tabungan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@OpenTextDialog
                }
                if (it.toDouble() < 0) {
                    Toast.makeText(context, "Tabungan tidak boleh negatif", Toast.LENGTH_SHORT).show()
                    return@OpenTextDialog
                }

                settingViewModel.updateSaving(it.toDouble())
                showEditTabunganDialog = false
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                   Text(
                       text = "Pengaturan",
                       style = MaterialTheme.typography.headlineSmall,
                       color = MaterialTheme.colorScheme.onSurface,
                   )

                    IconButton(
                        onClick = {
                            showConfirmationDialog = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            // goProfile()
                        }),
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 48.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(58.dp)
                            ) {
                                ImageWithLoadingIndicator(userProfileData?.photoUrl ?: "")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = userProfileData?.name ?: "User",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    text = userProfileData?.dob ?: "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    text = userProfileData?.email ?: "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                goProfile()
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isDarkModeEnabled) "Tema: Gelap" else "Tema: Terang",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = isDarkModeEnabled,
                            onCheckedChange = { isDarkMode ->
                                settingViewModel.updateDarkMode(isDarkMode)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))


                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Perbaharui Tabungan",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                showEditTabunganDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExitConfirmationDialog(onDismiss: () -> Unit, onConfirmExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Konfirmasi", style = MaterialTheme.typography.headlineMedium) },
        text = { Text("Apakah Anda yakin ingin logout dari aplikasi?", style = MaterialTheme.typography.bodyLarge) },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmExit()
                    onDismiss()
                }
            ) {
                Text("Ya", style = MaterialTheme.typography.bodyMedium)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Batal", style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}
