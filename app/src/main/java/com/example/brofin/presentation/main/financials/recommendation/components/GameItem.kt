package com.example.brofin.presentation.main.financials.recommendation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.brofin.domain.models.GameRecommendation
import com.example.brofin.ui.theme.BrofinTheme
import com.example.brofin.utils.toIndonesianCurrency2

@Composable
fun GameItem(
    game: GameRecommendation,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = game.nama ?: "Nama Tidak Diketahui",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Harga",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = ": ${game.harga?.toIndonesianCurrency2() ?: "Tidak Diketahui"}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.8f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Kata Kunci",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = ": ${game.kataKunci ?: "Tidak Diketahui"}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.8f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tipe Review",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = ": ${game.tipeReview ?: "Tidak Diketahui"}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.8f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ShowItem() {
    BrofinTheme {
        GameItem(
            game = GameRecommendation(
                nama = "Nama Game",
                harga = 100000.0,
                kataKunci = "Kata Kunci",
                tipeReview = "Tipe Review"
            )
        )
    }

}

