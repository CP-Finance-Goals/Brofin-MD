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
import com.example.brofin.domain.models.MobilRecommendation
import com.example.brofin.ui.theme.BrofinTheme
import com.example.brofin.utils.toIndonesianCurrency2

@Composable
fun MobilItem(
    mobil: MobilRecommendation,
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
                text = mobil.brand ?: "Brand Tidak Diketahui",
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
                    text = ": ${mobil.price?.toIndonesianCurrency2() ?: 0.0.toIndonesianCurrency2()}",
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
                    text = "Transmisi",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = ": ${mobil.transmisi ?: "Tidak Diketahui"}",
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
                    text = "Tipe Bahan Bakar",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = ": ${mobil.tipeBbm ?: "Tidak Diketahui"}",
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
        MobilItem(
            mobil = MobilRecommendation(
                brand = "Toyota",
                price = 200000000.0,
                tipeBbm = "Pertamax",
                transmisi = "Manual"
            )
        )
    }

}
