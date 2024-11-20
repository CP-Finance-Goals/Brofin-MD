package com.example.brofin.presentation.splash

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieConstants
import com.example.brofin.presentation.authentication.components.LottieAnimationOnce
import com.example.brofin.utils.AppFonts
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    goHome: () -> Unit,
    goLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        delay(2000) // Menunggu splash animasi selesai

        when (uiState.isUserLoggedIn) {
            true -> {
                // Jika user login, arahkan ke Home
                goHome()
            }
            false -> {
                // Jika user belum login, arahkan ke Login
                goLogin()
            }
            null -> {
                // Log jika state tidak valid
                Log.e("SplashScreen", "User logged in state is null")
            }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Tampilan utama SplashScreen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimationOnce(iterarion = LottieConstants.IterateForever)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SlidingText(text = "Brofin\nBudgeting and Financial App")
            }

            // Tampilkan indikator loading jika state belum siap
            if (uiState.isUserLoggedIn == null || uiState.userBalanceExist == null) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            // Tampilkan pesan error jika ada
            uiState.errorMessage?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun SlidingText(text: String) {
    val offsetX = remember { Animatable(300f) }

    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Text(
        text = text,
        style = AppFonts.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .offset { IntOffset(offsetX.value.toInt(), 0) }
    )
}
