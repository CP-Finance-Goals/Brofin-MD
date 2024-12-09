package com.example.brofin.presentation.main.financials.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.brofin.domain.models.Predict
import com.example.brofin.utils.calculateFutureYear
import com.example.brofin.utils.toIndonesianCurrency2
import com.example.brofin.utils.toIndonesianDate

@Composable
fun PredictTable(
    dataState: Predict,
    isHome: Boolean = false,
    ondelete: (Predict) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Prediksi Harga Rumah",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

               if (!isHome){
                   Row {
                       IconButton(onClick = { ondelete(dataState) }) {
                           Icon(
                               imageVector = Icons.Default.Delete,
                               contentDescription = "Delete",
                               tint = Color.Red
                           )
                       }


                   }
               }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = "Prediksi Harga Rumah tahun ${calculateFutureYear(currentMillis = dataState.datePredict, yearsToAdd = dataState.tahunTarget ?: 0)} dibuat pada Tanggal ${dataState.datePredict.toIndonesianDate()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Harga Rumah ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    text = ": ${dataState.predictedPrice?.toDouble()?.toIndonesianCurrency2() ?: "Tidak Tersedia" }",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Harga Rumah Setelah Inflasi",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    text = ": ${dataState.adjustedPrice?.toDouble()?.toIndonesianCurrency2() ?: "Tidak Tersedia"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Estimasi Cicilan Bulanan",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    text = ": ${dataState.cicilanBulanan?.toDouble()?.toIndonesianCurrency2() ?: "Tidak Tersedia"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rekomendasi Harga Rumah Untukmu",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    text = ": ${dataState.maxAffordablePrice?.toDouble()?.toIndonesianCurrency2() ?: "Tidak Tersedia"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
               Column {

                   Spacer(modifier = Modifier.height(16.dp))
                   Text(
                       text = "Rekomendasi KPR",
                       style = MaterialTheme.typography.titleMedium,
                       color = MaterialTheme.colorScheme.onBackground,
                       modifier = Modifier.padding(bottom = 8.dp)
                   )

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Suku Bunga",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.sukuBunga ?: "Tidak Tersedia"}%",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Tenor (tahun)",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.tenor ?: "Tidak Tersedia"} tahun",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "DP",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.dp?.toDouble()?.toIndonesianCurrency2() ?: "Tidak Tersedia"}",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Spacer(modifier = Modifier.height(16.dp))
                   Text(
                       text = "Spesifikasi Rumah",
                       style = MaterialTheme.typography.titleMedium,
                       color = MaterialTheme.colorScheme.onBackground,
                       modifier = Modifier.padding(bottom = 8.dp)
                   )

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Lokasi Rumah",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.kota ?: "Tidak Diketahui"}",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Jumlah Kamar Tidur",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.jumlahKamarTidur ?: "Tidak Tersedia"} kamar tidur",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Jumlah Kamar Mandi",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.jumlahKamarMandi ?: "Tidak Tersedia"} kamar mandi",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Ukuran Tanah (m²)",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.ukurantanah ?: "Tidak Diketahui"} m²",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Ukuran Bangunan (m²)",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.ukuranbangunan ?: "Tidak Diketahui"} m²",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Daya Listrik (Watt)",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.dayaListrik ?: "Tidak Tersedia"} Watt",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }

                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Jumlah Lantai",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.jumlahLantai ?: "Tidak Tersedia"} lantai",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Jumlah Kamar Pembantu",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${dataState.jumlahKamarPembantu ?: "Tidak Tersedia"}",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start,
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = "Tahun Target",
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onBackground,
                           modifier = Modifier.weight(1.2f)
                       )
                       Text(
                           text = ": ${calculateFutureYear(dataState.datePredict ,dataState.tahunTarget ?: 0)}",
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.primary,
                           overflow = TextOverflow.Ellipsis,
                           modifier = Modifier.weight(1f)
                       )
                   }
               }
            }
        }
    }
}

