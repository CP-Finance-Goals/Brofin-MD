package com.example.brofin.presentation.main.budget

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brofin.data.mapper.toBudgetingDiary
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.presentation.main.budget.components.BudgetAllocationCard
import com.example.brofin.presentation.main.budget.components.MonthSelected
import com.example.brofin.utils.BudgetAllocation
import com.example.brofin.utils.Expense.budgetAllocations
import com.example.brofin.utils.getCurrentMonthAndYearAsLong

@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    viewModel: BudgetViewModel = hiltViewModel(),
    goDetail: (List<BudgetingDiary>, BudgetAllocation, Double) -> Unit
) {

    val allocation = budgetAllocations
    val context = LocalContext.current
    var monthAndYear by remember {
        mutableLongStateOf(getCurrentMonthAndYearAsLong())
    }

    val budgetingWithDiaries by viewModel.getBudgetingDiariesByMonthAndYear(monthAndYear).collectAsStateWithLifecycle(null)

    val budgetingDiaries = budgetingWithDiaries?.diaries?.map {
        it .toBudgetingDiary()
    }

    val limitKeinginan = budgetingWithDiaries?.budgeting?.wantsLimit
    val limitKebutuhan = budgetingWithDiaries?.budgeting?.essentialNeedsLimit
    val limitTabungan = budgetingWithDiaries?.budgeting?.savingsLimit

    val diariesPokok = budgetingDiaries?.filter {
        it.categoryId == 1 || it.categoryId == 2 || it.categoryId == 4 || it.categoryId == 5 || it.categoryId == 7
    }

    val diariesKeinginan = budgetingDiaries?.filter {
        it.categoryId == 3 || it.categoryId == 6  || it.categoryId == 9
    }

    val listDiaries = listOf(diariesPokok, diariesKeinginan)


    val listBudget = listOf(limitKebutuhan, limitKeinginan, limitTabungan)

    val updatedAllocations = allocation.map { allocation ->
        val filteredDiaries = budgetingDiaries?.filter { diary ->
            allocation.kategori.any { it.id == diary.categoryId }
        } ?: emptyList()

        val updatedKategori = allocation.kategori.map { category ->
            val categoryBudgetUsed = filteredDiaries.filter { it.categoryId == category.id }
                .sumOf { it.amount }

            category.copy(budgetUsed = categoryBudgetUsed)
        }

        allocation.copy(
            kategori = updatedKategori
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
                itemsIndexed(updatedAllocations) { index, budget ->
                    BudgetAllocationCard(
                        allocation = budget,
                        modifier = Modifier.clickable(
                            onClick = {
                               if(diariesPokok != null && diariesKeinginan != null){
                                   if (budget.namaAlokasi == "Tabungan" ){
                                       Toast.makeText(
                                           context,
                                           "Tidak ada data untuk tabungan",
                                           Toast.LENGTH_SHORT
                                       ).show()
                                   } else {
                                       goDetail(listDiaries[index] ?: emptyList(), budget, listBudget[index] ?: 0.0)
                                   }
                               }

                               else{
                                   Toast.makeText(context, "Tidak ada data", Toast.LENGTH_SHORT).show()
                               }
                            }
                        ),
                        limit = listBudget[index] ?: 0.0
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

