package com.example.brofin.presentation.settings.components

import android.graphics.Color
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.brofin.presentation.detail.components.ImageWithLoadingIndicator

@Composable
fun SettingsItem(
    imageUrl: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Animasi ukuran gambar
    val animatedSizeDp by animateDpAsState(
        targetValue = if (isExpanded) 100.dp else 50.dp,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy
        ), label = "Size Animation"
    )

    // Padding yang berubah tergantung ekspansi
    val paddingValue = if (isExpanded) 16.dp else 8.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { isExpanded = !isExpanded }),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValue) // padding berubah tergantung isExpanded
        ) {
            // Menampilkan ikon dengan ukuran animasi
            Box(
                modifier = Modifier
                    .size(animatedSizeDp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally)
            ) {
                ImageWithLoadingIndicator(
                    diaryImageUrl = imageUrl,
                )
            }

            // Judul item pengaturan
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Deskripsi item pengaturan jika diperbesar
            if (isExpanded) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
