package com.example.brofin.presentation.authentication.register

import android.util.Patterns
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.presentation.authentication.AuthViewModel
import com.example.brofin.presentation.authentication.components.CustomTextField
import com.example.brofin.presentation.authentication.components.GoogleAuthButton
import com.example.brofin.presentation.authentication.components.IdentifierTextField
import com.example.brofin.presentation.authentication.components.LottieAnimationTwo
import com.example.brofin.presentation.authentication.state.AuthState
import com.example.brofin.presentation.components.LoadingDialog

@Composable
fun RegisterScreen(
    goBack: () -> Unit,
    goHome: () -> Unit,
    viewmodel: AuthViewModel = hiltViewModel()
) {

    val stateRegister = viewmodel.authState.collectAsStateWithLifecycle(initialValue = AuthState.Idle)

    var showLoadingDialog by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    var confirmPassword by remember { mutableStateOf("") }
    val animationAlpha = remember { Animatable(0f) }
    val nameAlpha = remember { Animatable(0f) }
    val emailAlpha = remember { Animatable(0f) }
    val passwordAlpha = remember { Animatable(0f) }
    val passwordAlpha2 = remember { Animatable(0f) }
    val buttonAlpha = remember { Animatable(0f) }
    val buttonAlpha3 = remember { Animatable(0f) }

    var snackbarMessage by remember { mutableStateOf<String?>("") }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmationFocusRequester = remember { FocusRequester() }

    val scrollState = rememberScrollState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        animationAlpha.animateTo(1f, animationSpec = tween(durationMillis = 700))
        nameAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        emailAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        passwordAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        passwordAlpha2.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        buttonAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        buttonAlpha3.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
    }


    LaunchedEffect(stateRegister.value) {
        when (val state = stateRegister.value) {
            is AuthState.Loading -> {
                showLoadingDialog = true
            }
            is AuthState.Success -> {
                showLoadingDialog = false
                snackbarMessage = "Registrasi berhasil"
                showSnackbar = true
                goHome()
            }
            is AuthState.Error -> {
                showLoadingDialog = false
                snackbarMessage = state.message.toString()
                showSnackbar = true
            }
            else -> {
                showLoadingDialog = false
            }
        }
    }

    LoadingDialog(
        showDialog = showLoadingDialog,
        onDismissRequest = { showLoadingDialog = false }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(300.dp)
                .offset(y = 120.dp)

            ) {
                LottieAnimationTwo()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 25.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    IconButton(
                        onClick = { goBack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Kembali",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .graphicsLayer(alpha = animationAlpha.value),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Buat Akun Brofin",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Daftar sekarang untuk mulai mengelola keuangan Anda dengan lebih mudah dan cerdas.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                CustomTextField(
                    label = IdentifierTextField.Name,
                    text = name,
                    onTextChange = { name = it },
                    validate = {
                        when {
                            it.isEmpty() -> "Nama tidak boleh kosong"
                            it.length < 3 -> "Nama harus lebih dari 2 karakter"
                            it.any { char -> char.isDigit() } -> "Nama tidak boleh mengandung angka"
                            else -> ""
                        }
                    },
                    modifier = Modifier
                        .graphicsLayer(alpha = nameAlpha.value)
                        .focusRequester(nameFocusRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { emailFocusRequester.requestFocus() })
                )
                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    label = IdentifierTextField.EMAIL,
                    text = email,
                    onTextChange = { email = it },
                    validate = {
                        if (it.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(it).matches())
                            "Email tidak valid."
                        else ""
                    },
                    modifier = Modifier
                        .graphicsLayer(alpha = emailAlpha.value)
                        .focusRequester(emailFocusRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() })
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(
                    label = IdentifierTextField.Password,
                    text = password,
                    onTextChange = { password = it },
                    isPassword = true,
                    validate = {
                        if (it.length < 8) "Kata sandi minimal 8 karakter." else ""
                    },
                    modifier = Modifier
                        .graphicsLayer(alpha = passwordAlpha.value)
                        .focusRequester(passwordFocusRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onDone = { confirmationFocusRequester.requestFocus() })
                )
                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    label = IdentifierTextField.ConfirmPassword,
                    text = confirmPassword,
                    onTextChange = { confirmPassword = it },
                    isPassword = true,
                    validate = {
                        if (it != password) "Kata sandi tidak cocok." else ""
                    },
                    modifier = Modifier
                        .graphicsLayer(alpha = passwordAlpha2.value)
                        .focusRequester(confirmationFocusRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                Spacer(modifier = Modifier.height(24.dp))


                Button(
                    onClick = {
                        keyboardController?.hide()
                        val nameError = when {
                            name.isBlank() -> "Nama tidak boleh kosong"
                            name.length < 3 -> "Nama harus lebih dari 2 karakter"
                            name.any { char -> char.isDigit() } -> "Nama tidak boleh mengandung angka"
                            else -> null
                        }

                        val emailError = when {
                            email.isBlank() -> "Email tidak boleh kosong"
                            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email tidak valid"
                            else -> null
                        }

                        val passwordError = when {
                            password.isBlank() -> "Password tidak boleh kosong"
                            password.length < 8 -> "Password harus lebih dari 8 karakter"
                            else -> null
                        }

                        val confirmPasswordError = when {
                            confirmPassword.isBlank() -> "Konfirmasi kata sandi tidak boleh kosong"
                            confirmPassword != password -> "Kata sandi tidak cocok"
                            else -> null
                        }

                        snackbarMessage = nameError ?: emailError ?: passwordError ?: confirmPasswordError

                        if (snackbarMessage != null) {
                            showSnackbar = true
                        } else {
                            viewmodel.registerWithEmail(name, email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .graphicsLayer(alpha = buttonAlpha.value),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Daftar")
                }


                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { },
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = buttonAlpha.value)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(
                    onClick = { goBack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .graphicsLayer(alpha = buttonAlpha.value),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Sudah punya akun? Masuk")
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                GoogleAuthButton(
                    modifier = Modifier
                        .graphicsLayer(alpha = buttonAlpha3.value)
                        .fillMaxWidth()
                        .height(50.dp),
                    buttonText = "Daftar dengan Google",
                    onClick = {

                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (showSnackbar) {
                    LaunchedEffect(snackbarMessage) {
                        snackbarMessage?.let { snackbarHostState.showSnackbar(it) }
                        showSnackbar = false
                    }
                }

            }
        }
    }
}
