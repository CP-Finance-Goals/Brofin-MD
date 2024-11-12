package com.example.brofin.presentation.main.budget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PriorityButton(
    percentage: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    buttonIsActive: Boolean = true
) {
    val backgroundColor = if (buttonIsActive) {
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    }

    val textColor = if (buttonIsActive) {
        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }

    Box(
        modifier = Modifier
            .height(100.dp)
            .background(
                color = backgroundColor,
                RoundedCornerShape(8.dp)
            )
            .clickable {
                if (buttonIsActive) {
                    onClick()
                }
            }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.headlineMedium,
                color = textColor
            )
        }
    }
}