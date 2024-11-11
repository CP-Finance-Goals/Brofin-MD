package com.example.brofin.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),       // Ungu terang, warna utama
    onPrimary = Color.Black,            // Warna teks pada warna utama
    secondary = Color(0xFF03DAC6),      // Turquoise, warna sekunder
    onSecondary = Color.Black,          // Warna teks pada warna sekunder
    tertiary = Color(0xFFCF6679),       // Merah muda, warna pelengkap
    onTertiary = Color.White,           // Warna teks pada warna pelengkap
    background = Color(0xFF121212),     // Warna latar gelap
    onBackground = Color(0xFFEDEDED),   // Warna teks pada latar belakang
    surface = Color(0xFF1E1E1E),        // Warna permukaan (surface) yang gelap
    onSurface = Color(0xFFEDEDED),      // Warna teks pada surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),        // Ungu utama untuk mode terang
    onPrimary = Color.White,            // Warna teks pada warna utama
    secondary = Color(0xFF03DAC6),      // Turquoise, warna sekunder
    onSecondary = Color.Black,          // Warna teks pada warna sekunder
    tertiary = Color(0xFFB00020),       // Merah cerah, warna pelengkap
    onTertiary = Color.White,           // Warna teks pada warna pelengkap
    background = Color(0xFFFFFBFE),     // Latar belakang terang
    onBackground = Color(0xFF1C1B1F),   // Warna teks pada latar belakang
    surface = Color(0xFFFFFFFF),        // Warna permukaan terang
    onSurface = Color(0xFF1C1B1F),      // Warna teks pada surface
)

@Composable
fun BrofinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}