package com.example.brofin.presentation.authentication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import com.example.brofin.R

@Composable
fun LottieAnimationOnce(iterarion: Int = 1) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_animate))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LottieAnimation(
            composition = composition,
            iterations = iterarion,
            speed = 0.8f,
        )
    }
}

@Composable
fun LottieAnimationTwo() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottieflow_background))

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth()
    )

}
