package com.example.brofin.presentation.main.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.brofin.presentation.main.budget.components.BottomSheetContent
import com.example.brofin.presentation.main.budget.components.BudgetItem
import com.example.brofin.presentation.main.budget.components.MonthSelected
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(modifier: Modifier = Modifier) {
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()




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

            MonthSelected()

            BudgetItem(
                categoryName = "Shopping",
                remainingAmount = "$0",
                totalSpent = "$1200",
                budgetLimit = "$1000",
                isOverLimit = true,
                progressColor = Color(0xFFFFA726),
                progress = 1f
            )
            BudgetItem(
                categoryName = "Transportation",
                remainingAmount = "$350",
                totalSpent = "$350",
                budgetLimit = "$700",
                isOverLimit = false,
                progressColor = Color(0xFF42A5F5),
                progress = 0.5f
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            bottomSheetState.show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Buat Budget")
                }
            }
        }
    }

    if (bottomSheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch { bottomSheetState.hide() }
            },
            sheetState = bottomSheetState
        ) {
            BottomSheetContent()
        }
    }
}


