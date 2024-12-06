package com.example.brofin.presentation.main.financials.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.borderDashed(
    width: Dp,
    color: Color,
    dashLength: Float = 10f,
    gapLength: Float = 6f
): Modifier = this.then(
    Modifier.drawBehind {
        val size = this.size
        val cornerRadius = 16.dp.toPx()

        val paint = Paint().apply {
            this.color = color
            this.style = Stroke
            this.strokeWidth = width.toPx()
            this.pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength))
        }

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius
                )
            )
        }

        drawIntoCanvas { canvas ->
            canvas.drawPath(path, paint)
        }
    }
)
