package com.example.brofin.presentation.main.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.utils.Expense
import com.example.brofin.utils.toFormattedDate
import com.example.brofin.utils.toIndonesianCurrency

@Composable
fun ListTransactions(budgetList: List<BudgetingDiary?>, goList: () -> Unit) {
    if (budgetList.isEmpty()) {
       EmptyListTransaction()
    } else {
       Card(
           modifier = Modifier
               .fillMaxWidth()
               .padding(vertical = 4.dp),
           shape = MaterialTheme.shapes.large,
           elevation = CardDefaults.cardElevation(
               defaultElevation = 6.dp
           )
       ) {
           Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
           ) {
               Text(
                   text = "Transaksi Terakhir",
                   style = MaterialTheme.typography.bodyMedium,
                   color = MaterialTheme.colorScheme.onSecondary,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.padding(16.dp),
               )

               Button(
                   onClick = {
                          goList()
                   },
                   modifier = Modifier
                       .padding(horizontal = 16.dp)
                       .align(Alignment.CenterVertically),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = MaterialTheme.colorScheme.primary,
                   ),
                   border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
               ) {
                   Text(
                       text = "Lihat semua",
                       style = MaterialTheme.typography.bodySmall,
                       fontWeight = FontWeight.Bold,
                          color = MaterialTheme.colorScheme.onPrimary
                   )
               }
           }
           LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                    )
           ) {
                items(budgetList.take(3)) { budgetingDiary ->
                     if (budgetingDiary != null) {
                          DiaryItem(budgetingDiary = budgetingDiary)
                          if (budgetList.indexOf(budgetingDiary) != budgetList.size - 1) {
                              HorizontalDivider(
                                  modifier = Modifier.padding(horizontal = 16.dp),
                                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                              )
                          }
                     }
                }
           }
       }
    }
}

@Composable
fun DiaryItem(budgetingDiary: BudgetingDiary) {
    val date =  budgetingDiary.date.toFormattedDate()
    fun trimToMaxWords(text: String, maxWords: Int): String {
        val words = text.split(" ")
        return if (words.size > maxWords) {
            words.take(maxWords).joinToString(" ") + "..."
        } else {
            text
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = Expense.getNameById(budgetingDiary.categoryId) ?: "Tidak diketahui",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (budgetingDiary.description != null){
                Text(
                    text = trimToMaxWords(budgetingDiary.description, 5),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        Text(
            text = "-${budgetingDiary.amount.toIndonesianCurrency()}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Red.copy(alpha = 0.7f)
        )

    }


}

