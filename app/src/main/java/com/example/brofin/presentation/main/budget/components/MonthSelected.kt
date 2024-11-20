package com.example.brofin.presentation.main.budget.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun MonthSelected(
    onSelectedMonthChange: (Long) -> Unit
) {
    var currentMonthIndex by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var currentYear by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }

    val calendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, currentMonthIndex)
        set(Calendar.YEAR, currentYear)
    }

    val formattedMonthAndYear = SimpleDateFormat("MMMM yyyy", Locale("id", "ID")).format(calendar.time)

    LaunchedEffect(calendar.timeInMillis) {
        onSelectedMonthChange(calendar.timeInMillis)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                if (currentMonthIndex == 0) {
                    currentMonthIndex = 11
                    currentYear -= 1
                } else {
                    currentMonthIndex -= 1
                }
            },
            colors = IconButtonDefaults.iconButtonColors().copy(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color.Transparent
            )
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }

        Text(
            text = formattedMonthAndYear,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        IconButton(
            onClick = {
                if (currentMonthIndex == 11) {
                    currentMonthIndex = 0
                    currentYear += 1
                } else {
                    currentMonthIndex += 1
                }
            },
            colors = IconButtonDefaults.iconButtonColors().copy(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color.Transparent
            )
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
        }
    }

}