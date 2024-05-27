package com.jumpingphantom.flow.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jumpingphantom.flow.R
import com.jumpingphantom.flow.util.Util

@Composable
fun BalanceSummary(incomeAmount: Float, expenseAmount: Float) {
    ExpensesAndIncomePieChart(incomeAmount, expenseAmount)
    Spacer(modifier = Modifier.height(16.dp))
    CurrentBalance(incomeAmount, expenseAmount)
}

@Composable
fun ExpensesAndIncomePieChart(incomeAmount: Float, expenseAmount: Float) {
    Surface(modifier = Modifier.size(width = 300.dp, height = 300.dp)) {
        val incomeColor = MaterialTheme.colorScheme.primary
        val expensesColor = MaterialTheme.colorScheme.primaryContainer
        val backgroundColor = MaterialTheme.colorScheme.background

        Column {
            Canvas(modifier = Modifier.size(300.dp)) {
                val sum = incomeAmount + expenseAmount
                var startAngle = -90f
                var sweepAngle = calculateSweepAngle(incomeAmount, 100.0f)

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

                    drawCircle(color = backgroundColor, radius = size.height - 520.0f)
                }
            }
        }
    }
}

fun calculateSweepAngle(sliceValue: Float, totalValue: Float): Float {
    return (sliceValue / totalValue) * 360f
}

@Composable
fun CurrentBalance(incomeAmount: Float, expenseAmount: Float) {
    val amount by remember { mutableFloatStateOf(incomeAmount - expenseAmount) }

    Card(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.income),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )

                    Text(text = Util.formatCurrency(incomeAmount), modifier = Modifier)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.expenses),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                    )

                    Text(text = Util.formatCurrency(expenseAmount), modifier = Modifier)
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.balance),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Text(
                        text = Util.formatCurrency(amount),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}