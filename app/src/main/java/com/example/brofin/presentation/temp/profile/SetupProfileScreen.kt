package com.example.brofin.presentation.temp.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.presentation.authentication.components.CircularProfileImage
import com.example.brofin.presentation.authentication.state.AuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupProfileScreen(
    viewModel: SetupProfileViewModel = hiltViewModel(),
    goHome: () -> Unit,
    goToLogin: () -> Unit
) {

    val uthState = viewModel.authStateFlow.collectAsStateWithLifecycle()

    viewModel.getCurrentUser { userExist ->
        if (!userExist) {
            goToLogin()
        }
    }

    val context = LocalContext.current

    val name = viewModel.name.collectAsStateWithLifecycle()
    val profilePicture = viewModel.profilePicture.collectAsStateWithLifecycle()
    val email = viewModel.email.collectAsStateWithLifecycle()

    var nameUpdate by remember {
        mutableStateOf(name.value)
    }

    var showDialog by remember { mutableStateOf(false) }

    var phoneNumber by remember { mutableStateOf("") }

    LaunchedEffect(uthState.value) {
        when(val state = uthState.value) {
            is AuthState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            AuthState.Loading -> {

            }
            AuthState.Success -> {

            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Setup Profile") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            CircularProfileImage(
                imageUrl = profilePicture.value ?: "",
            )

            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = nameUpdate,
                onValueChange = { nameUpdate = it },
                label = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email.value,
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Nomor Telepon") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Profil berhasil disimpan", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Profil")
            }
        }
    }
}
