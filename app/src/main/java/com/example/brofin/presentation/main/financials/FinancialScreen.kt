package com.example.brofin.presentation.main.financials

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import com.example.brofin.presentation.main.financials.components.FinancialGoalCard
import com.example.brofin.utils.dummyGoals

@Composable
fun FinancialScreen(
    modifier: Modifier = Modifier,
    goAddFinancialPlan: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        FinancialGoalList(
            goals = dummyGoals
        ) {
            Toast.makeText(
                context,
                "Goal ${it.name} clicked",
                Toast.LENGTH_SHORT
            ).show()
        }

        Button(
            onClick = {
                goAddFinancialPlan()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(
                    alignment = Alignment.BottomEnd
                )
        ) {
            Text(text = "Add Financial Plan")
        }
    }
}

@Composable
fun FinancialGoalList(goals: List<FinancialGoalsEntity>, onGoalClick: (FinancialGoalsEntity) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(goals.size) { index ->
            FinancialGoalCard(goal = goals[index]) {
                onGoalClick(goals[index])
            }
        }
    }
}