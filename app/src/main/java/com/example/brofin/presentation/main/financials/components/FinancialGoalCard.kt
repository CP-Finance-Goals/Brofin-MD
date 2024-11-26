package com.example.brofin.presentation.main.financials.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FinancialGoalCard(goal: FinancialGoalsEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Nama Goal
            Text(
                text = goal.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Deskripsi Goal
            if (!goal.description.isNullOrEmpty()) {
                Text(
                    text = goal.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Target dan Progres
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Target: Rp${goal.targetAmount}")
                Text(text = "Terkumpul: Rp${goal.savedAmount}")
            }

            // Cicilan
            Text(
                text = "Cicilan: Rp${goal.installment}/bulan",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Deadline
            if (goal.deadline != null) {
                val formattedDeadline = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    .format(Date(goal.deadline))
                Text(
                    text = "Deadline: $formattedDeadline",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Status
            val statusText = if (goal.isCompleted) "Status: Selesai" else "Status: Dalam Progres"
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyMedium,
                color = if (goal.isCompleted) Color.Green else Color.Red
            )
        }
    }
}
