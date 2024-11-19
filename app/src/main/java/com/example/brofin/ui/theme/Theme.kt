package com.example.brofin.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.brofin.utils.AppFonts

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4A5568),       // Abu-abu gelap sebagai warna utama
    onPrimary = Color.White,           // Warna teks pada warna utama
    secondary = Color(0xFF2D3748),     // Abu-abu kebiruan sebagai warna sekunder
    onSecondary = Color.White,         // Warna teks pada warna sekunder
    tertiary = Color(0xFF1E293B),      // Abu-abu biru gelap sebagai warna pelengkap
    onTertiary = Color.White,          // Warna teks pada warna pelengkap
    background = Color(0xFF1A202C),    // Warna latar belakang gelap
    onBackground = Color(0xFFE2E8F0),  // Warna teks pada latar belakang (abu-abu terang)
    surface = Color(0xFF2D3748),       // Warna permukaan gelap yang netral
    onSurface = Color(0xFFE2E8F0),     // Warna teks pada permukaan (abu-abu terang)
    primaryContainer = Color(0xFF2D3748) // Warna kontainer utama
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A5568),       // Abu-abu gelap untuk mode terang
    onPrimary = Color.White,           // Warna teks pada warna utama
    secondary = Color(0xFF2D3748),     // Abu-abu kebiruan sebagai warna sekunder
    onSecondary = Color.White,         // Warna teks pada warna sekunder
    tertiary = Color(0xFF2B6CB0),      // Biru tua sebagai warna pelengkap
    onTertiary = Color.White,          // Warna teks pada warna pelengkap
    background = Color(0xFFF7FAFC),    // Warna latar terang
    onBackground = Color(0xFF2D3748),  // Warna teks pada latar belakang
    surface = Color(0xFFFFFFFF),       // Warna permukaan terang
    onSurface = Color(0xFF2D3748),     // Warna teks pada permukaan terang
    primaryContainer = Color(0xFFE2E8F0) // Warna kontainer utama
)


@Composable
fun BrofinTheme(
    darkTheme: Boolean? = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme == true) {
        DarkColorScheme
    } else if(darkTheme == false) {
        LightColorScheme
    } else {
        if (isSystemInDarkTheme()) {
            DarkColorScheme
        } else {
            LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppFonts,
        content = content
    )
}

