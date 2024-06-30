package com.jumpingphantom.flow.features.statistics.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp

@Composable
fun Statistics() {
    PieChart(incomeAmount = 50f, expenseAmount = 25f)
}

@Composable
fun PieChart(incomeAmount: Float, expenseAmount: Float) {
    if (incomeAmount != 0f || expenseAmount != 0f) {
        val circleSize = 320.dp
        val incomeColor = MaterialTheme.colorScheme.primary
        val expensesColor = MaterialTheme.colorScheme.primaryContainer
        val backgroundColor = MaterialTheme.colorScheme.background

        Canvas(modifier = Modifier.size(circleSize)) {
            val sum = incomeAmount + expenseAmount
            var startAngle = -90f
            var sweepAngle = calculateSweepAngle(incomeAmount, sum)
            scale(0.8f) {
                drawArc(
                    color = incomeColor,
                    useCenter = true,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                )
                startAngle += sweepAngle
                sweepAngle = calculateSweepAngle(expenseAmount, sum)
                drawArc(
                    color = expensesColor,
                    useCenter = true,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                )
                drawCircle(color = backgroundColor, radius = circleSize.toPx() / 2.5f)
            }
        }
    } else {
        Text(text = "¯\\_(ツ)_/¯", style = MaterialTheme.typography.displayLarge)
    }
}

fun calculateSweepAngle(sliceValue: Float, totalValue: Float): Float {
    return (sliceValue / totalValue) * 360f
}
