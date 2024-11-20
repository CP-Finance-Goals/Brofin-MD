package com.example.brofin.presentation.main.budget

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.presentation.main.budget.components.BudgetAllocationCard
import com.example.brofin.presentation.main.budget.components.MonthSelected
import com.example.brofin.utils.Expense
import com.example.brofin.utils.Expense.budgetAllocations
import com.example.brofin.utils.getCurrentMonthAndYearAsLong

@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    viewModel: BudgetViewModel = hiltViewModel()
) {

    val allocation = budgetAllocations
    val context = LocalContext.current
    var monthAndYear by remember {
        mutableLongStateOf(getCurrentMonthAndYearAsLong())
    }

    val budgetingWithDiaries by viewModel.getBudgetingDiariesByMonthAndYear(monthAndYear).collectAsStateWithLifecycle(null)

    val budget = budgetingWithDiaries?.budgeting
    val budgetingDiaries = budgetingWithDiaries?.diaries

    val kebutuhanPokok = budgetingDiaries?.filter { diary ->
        budgetAllocations[0].kategori.any { it.id == diary.categoryId }
    }

    val keinginan = budgetingDiaries?.filter { diary ->
        budgetAllocations[1].kategori.any { it.id == diary.categoryId }
    }

    val tabungan = budget?.savingsLimit

    val updatedAllocations = allocation.map { allocation ->
        val filteredDiaries = budgetingDiaries?.filter { diary ->
            allocation.kategori.any { it.id == diary.categoryId }
        } ?: emptyList()

        val updatedKategori = allocation.kategori.map { category ->
            val categoryBudgetUsed = filteredDiaries.filter { it.categoryId == category.id }
                .sumOf { it.amount } // Total pengeluaran untuk kategori ini

            category.copy(budgetUsed = categoryBudgetUsed) // Perbarui budgetUsed
        }

        allocation.copy(
            kategori = updatedKategori // Perbarui kategori dengan budgetUsed
        )
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            MonthSelected(
                onSelectedMonthChange = { selectedMonth ->
                    monthAndYear = getCurrentMonthAndYearAsLong(selectedMonth)
                    Toast.makeText(context, "Selected month: $monthAndYear", Toast.LENGTH_SHORT).show()
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(updatedAllocations) { budget ->
                    BudgetAllocationCard(
                        allocation = budget,
                        modifier = Modifier.clickable(
                            onClick = {
                                Toast.makeText(context, "Clicked ${budget.namaAlokasi}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

