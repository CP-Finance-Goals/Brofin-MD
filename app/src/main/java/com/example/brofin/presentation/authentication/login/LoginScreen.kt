package com.example.brofin.presentation.authentication.login

import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
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
import com.example.brofin.presentation.authentication.components.LottieAnimationOnce
import com.example.brofin.presentation.authentication.components.LottieAnimationTwo
import com.example.brofin.presentation.authentication.state.AuthState
import com.example.brofin.presentation.components.LoadingDialog
import com.stevdzasan.onetap.OneTapGoogleButton
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import kotlin.text.isBlank
import kotlin.text.isNotBlank

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    goToRegister: () -> Unit,
    goHome: () -> Unit,
    viewmodel: AuthViewModel = hiltViewModel(),
) {

    val state by viewmodel.authState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    var snackbarMessage by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val animationAlpha = remember { Animatable(0f) }
    val emailAlpha = remember { Animatable(0f) }
    val passwordAlpha = remember { Animatable(0f) }
    val buttonAlpha = remember { Animatable(0f) }
    val buttonAlpha2 = remember { Animatable(0f) }
    val buttonAlpha3 = remember { Animatable(0f) }
    val dividerAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }

    // stateScroll
    val stateScroll = rememberScrollState()

    var showLoadingDialog by remember { mutableStateOf(false) }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        animationAlpha.animateTo(1f, animationSpec = tween(durationMillis = 700))
        textAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        emailAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        passwordAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        buttonAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        buttonAlpha2.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        buttonAlpha3.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
        dividerAlpha.animateTo(1f, animationSpec = tween(durationMillis = 200, delayMillis = 40))
    }

    LaunchedEffect(state) {
        when (val stateData = state) {
            is AuthState.Loading -> {
                showLoadingDialog = true
            }
            is AuthState.Success -> {
                showLoadingDialog = false
                goHome()
            }
            is AuthState.Error -> {
                showLoadingDialog = false
                snackbarMessage = stateData.message.toString()
                showSnackbar = true
            }
            else -> {
                showLoadingDialog = false
            }
        }
    }
    val stateGoogle = OneTapSignInState()


    OneTapSignInWithGoogle(
        state = stateGoogle,
        clientId = "911582459244-gelvcg4s6jlemlclpgfjf876ehm9c95t.apps.googleusercontent.com",
        onTokenIdReceived = {
            viewmodel.loginWithGoogle(it)
        },
        onDialogDismissed = { error ->
            Toast.makeText(context, "Dialog dismissed: $error", Toast.LENGTH_SHORT).show()
        }
    )


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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-90).dp)
                    .size(400.dp)
            ) {
                LottieAnimationOnce()
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(stateScroll)
                    .padding(horizontal = 25.dp)
            ) {
                Spacer(modifier = Modifier.height(220.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp)
                        .graphicsLayer(alpha = animationAlpha.value),
                    colors = CardDefaults.cardColors().copy(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Brofin: Solusi Pintar untuk Keuangan Anda",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Field Email
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

                // Field Password
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
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Login Masbro
                Button(
                    onClick = {
                        keyboardController?.hide()
                        if (email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                            password.length >= 8 ) {
                            viewmodel.loginWithEmail(email, password)
                        } else {
                            snackbarMessage = when {
                                email.isBlank() -> "Email tidak boleh kosong"
                                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email tidak valid"
                                password.length < 8 -> "Password harus lebih dari 8 karakter"
                                else -> "Input tidak valid"
                            }

                            showSnackbar = true

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .graphicsLayer(alpha = buttonAlpha.value),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor =  MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Masuk", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(alpha = dividerAlpha.value),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tombol daftare
                Button(
                    onClick = {
                        goToRegister()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .graphicsLayer(alpha = buttonAlpha2.value),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondary,
//                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(text = "Daftar Akun", color = MaterialTheme.colorScheme.onSecondary)
                }
                Spacer(modifier = Modifier.height(16.dp))

                GoogleAuthButton(
                    modifier = Modifier
                        .graphicsLayer(alpha = buttonAlpha3.value)
                        .fillMaxWidth()
                        .height(50.dp),
                    buttonText = "Masuk dengan Google",
                    onClick = {
                        stateGoogle.open()
                    }
                )



                if (showSnackbar) {
                    LaunchedEffect(snackbarMessage) {
                        snackbarHostState.showSnackbar(snackbarMessage)
                        showSnackbar = false
                    }
                }
            }
        }
    }
}
