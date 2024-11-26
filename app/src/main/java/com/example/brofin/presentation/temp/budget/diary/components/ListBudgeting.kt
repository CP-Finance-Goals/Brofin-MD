package com.example.brofin.presentation.temp.budget.diary.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.utils.toIndonesianCurrency
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListBudgeting(
    modifier: Modifier = Modifier,
    listBudgeting: List<BudgetingDiary?>
) {
    if (listBudgeting.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tidak ada transaksi",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(listBudgeting) { budgetingDiary ->
                budgetingDiary?.let {
                    BudgetItem(budgetingDiary = it)
                }
            }
        }
    }
}

@Composable
fun BudgetItem(budgetingDiary: BudgetingDiary) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = dateFormat.format(Date(budgetingDiary.date))
    val amountColor = Color.Red.copy(alpha = 0.7f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp),
           verticalArrangement = Arrangement.spacedBy(8.dp)
       ) {
           Row(
               modifier = Modifier
                   .fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ) {
               Column {
                   if (budgetingDiary.description != null){
                       Text(
                           text = budgetingDiary.description,
                           style = MaterialTheme.typography.bodyLarge,
                           fontWeight = FontWeight.Bold
                       )
                   }
                   Text(
                       text = date,
                       style = MaterialTheme.typography.bodySmall,
                       color = Color.Gray
                   )
               }

               Text(
                   text = "-${budgetingDiary.amount.toIndonesianCurrency()}",
                   style = MaterialTheme.typography.bodyMedium,
                   fontWeight = FontWeight.Bold,
                   color = amountColor
               )

           }
          if (budgetingDiary.description != null){
              Text(
                  text = budgetingDiary.description,
                  style = MaterialTheme.typography.bodyMedium,
                  textAlign = TextAlign.Start
              )
          }
       }
    }
}
