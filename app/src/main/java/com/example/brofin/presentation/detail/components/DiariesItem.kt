package com.example.brofin.presentation.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.brofin.R
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.utils.Expense
import com.example.brofin.utils.toFormattedDate
import com.example.brofin.utils.toIndonesianCurrency2

@Composable
fun DiariesItem(
    diary: BudgetingDiary,
    modifier: Modifier = Modifier,
    color: Color
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
            ) {
                AsyncImage(
                    model = diary.photoUri,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Photo",
                    error = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
                )
            }


            Spacer(modifier = Modifier.width(12.dp))

            // Informasi Utama
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                // categori
                Text(
                    text = Expense.getNameById(diary.categoryId) ?: "Kategori tidak ditemukan",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
                // Deskripsi
                if ( diary.description != null) {
                    Text(
                        text = diary.description,
                        style = MaterialTheme.typography.bodySmall,
                           color = MaterialTheme.colorScheme.onSurfaceVariant,
                           maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                   Spacer(modifier = Modifier.height(4.dp))
                }

                // Tanggal
                Text(
                    text =  diary.date.toFormattedDate(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Jumlah Uang
            Text(
                text = diary.amount.toIndonesianCurrency2(),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red.copy(alpha = 0.7f),
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
