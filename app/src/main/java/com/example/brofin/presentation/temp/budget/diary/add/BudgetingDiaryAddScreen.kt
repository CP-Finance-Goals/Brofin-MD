package com.example.brofin.presentation.temp.budget.diary.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BudgetingDiaryAddScreen(modifier: Modifier = Modifier) {
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "Add Budgeting Diary Entry") },
//                navigationIcon = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = modifier
//                .padding(innerPadding)
//                .padding(16.dp)
//                .fillMaxSize()
//        ) {
//            BudgetingDiaryAddForm()
//        }
//    }
//
//}