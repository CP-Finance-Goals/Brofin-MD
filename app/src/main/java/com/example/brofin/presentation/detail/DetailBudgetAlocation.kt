package com.example.brofin.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.presentation.detail.components.DiariesItem
import com.example.brofin.utils.BudgetAllocation
import com.example.brofin.utils.CategoryExpenses
import com.example.brofin.utils.toIndonesianCurrency2

fun updateAllocationWithDiaries(
    allocation: BudgetAllocation,
    diaries: List<BudgetingDiary>
): BudgetAllocation {
    val updatedKategori = allocation.kategori.map { category ->
        val totalUsed = diaries
            .filter { it.categoryId == category.id }
            .sumOf { it.amount }
        category.copy(budgetUsed = totalUsed)
    }
    return allocation.copy(kategori = updatedKategori)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBudgetAllocation(
    limit: Double,
    allocation: BudgetAllocation,
    diaries: List<BudgetingDiary>,
    onBack: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<CategoryExpenses?>(null) }
    val updatedAllocation = updateAllocationWithDiaries(allocation, diaries)
    val totalBudgetUsed = updatedAllocation.kategori.sumOf { it.budgetUsed }
    val remainingBudget = limit - totalBudgetUsed

    val backgroundColor = MaterialTheme.colorScheme.background

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = updatedAllocation.namaAlokasi,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BudgetAllocationCard(
                updatedAllocation = updatedAllocation,
                limit = limit,
                totalBudgetUsed = totalBudgetUsed,
                remainingBudget = remainingBudget
            )

            // Kategori
            Text(
                text = "Kategori:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                itemsIndexed(updatedAllocation.kategori) { index, category ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(80.dp)
                            .background(
                                color = Color(android.graphics.Color.parseColor(category.warna)),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp)
                            .clickable {
                                selectedCategory = category
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = category.namaKategori,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (category.budgetUsed > 0) {
                                    "-${category.budgetUsed.toIndonesianCurrency2()}"
                                } else {
                                    "-Rp 0"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    if (index == updatedAllocation.kategori.size - 1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Daftar Pengeluaran:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(4.dp),
                color = Color.Gray
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                itemsIndexed(
                    diaries.filter { diary ->
                        // Filter berdasarkan kategori yang dipilih (jika ada)
                        selectedCategory == null || diary.categoryId == selectedCategory?.id
                    }
                ) { index, diary ->
                    val category = updatedAllocation.kategori.find { it.id == diary.categoryId }
                    val categoryColor = category?.warna?.let { Color(android.graphics.Color.parseColor(it)) }
                        ?: MaterialTheme.colorScheme.surface // Jika kategori tidak ditemukan, gunakan warna default

                    DiariesItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = categoryColor,
                                shape = MaterialTheme.shapes.medium
                            ),
                        diary = diary,
                        color = categoryColor
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun BudgetAllocationCard(updatedAllocation: BudgetAllocation, limit: Double, totalBudgetUsed: Double, remainingBudget: Double) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium), // Menambahkan bayangan untuk efek kedalaman
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = colors.surface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = updatedAllocation.deskripsi,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onBackground,
                modifier = Modifier.padding(bottom = 16.dp) // Memberikan jarak antar deskripsi dan progress
            )

            LinearProgressIndicator(
                progress = { (totalBudgetUsed / limit).toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = colors.primary,
                trackColor = colors.onSurface.copy(alpha = 0.1f) // Warna track yang lebih lembut
            )

            Spacer(modifier = Modifier.height(16.dp)) // Spacer untuk jarak antar komponen

            // Informasi limit dan budget
            Text(
                text = "Total limit: ${limit.toIndonesianCurrency2()}",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onBackground,
                modifier = Modifier.padding(bottom = 8.dp) // Memberikan jarak antar teks
            )

            Text(
                text = "Digunakan: ${totalBudgetUsed.toIndonesianCurrency2()}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 8.dp) // Memberikan jarak antar teks
            )

            Text(
                text = "Tersisa: ${remainingBudget.toIndonesianCurrency2()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

