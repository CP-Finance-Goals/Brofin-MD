package com.example.brofin.presentation.main.budget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary

@Composable
fun DetailAllocation(budgetAllocationId: Int, bugeting: Budgeting, diaries: List<BudgetingDiary>) {


    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ){

        }
    }

}