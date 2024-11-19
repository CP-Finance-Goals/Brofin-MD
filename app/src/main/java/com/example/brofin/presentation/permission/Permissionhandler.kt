package com.example.brofin.presentation.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    multiplePermissionsState: MultiplePermissionsState,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (text: String) -> Unit,
    onPermissionRevoked: (text: String) -> Unit,
) {
    val context = LocalContext.current

//    LaunchedEffect(Unit) {
//        if (!multiplePermissionsState.allPermissionsGranted) {
//            multiplePermissionsState.launchMultiplePermissionRequest()
//        }
//    }

    LaunchedEffect(multiplePermissionsState) {
        when {
            multiplePermissionsState.allPermissionsGranted -> {
                onPermissionGranted()
            }
            multiplePermissionsState.shouldShowRationale -> {
                onPermissionDenied("Izin diperlukan untuk melanjutkan. Silakan berikan izin.")
            }
            else -> {
                onPermissionRevoked("Izin aplikasi telah ditolak secara permanen. Buka Pengaturan untuk mengaktifkan kembali izin.")
            }
        }
    }

    if (!multiplePermissionsState.allPermissionsGranted) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Izin Diperlukan") },
            text = {
                Text("Aplikasi memerlukan izin untuk mengakses fitur ini. Jika ditolak, aplikasi tidak dapat bekerja dengan optimal.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }
                ) {
                    Text("Izinkan")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        context.startActivity(intent)
                    }
                ) {
                    Text("Buka Pengaturan")
                }
            }
        )
    }
}
