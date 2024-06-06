package com.jumpingphantom.flow.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jumpingphantom.flow.R
import com.jumpingphantom.flow.util.Util
import com.jumpingphantom.flow.viewmodel.TransactionViewmodel

@Composable
fun BalanceSummary(
    transactionViewmodel: TransactionViewmodel
) {
    val income = remember { mutableFloatStateOf(0.0f) }
    val expenses = remember { mutableFloatStateOf(0.0f) }

    transactionViewmodel.incomeTotal.observe(LocalLifecycleOwner.current) {
        income.floatValue = it
    }

    transactionViewmodel.expensesTotal.observe(LocalLifecycleOwner.current) {
        expenses.floatValue = it
    }

    Card(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            IncomeAndExpensesPieChart(income.floatValue, expenses.floatValue)
            IncomeAndExpenses(income, expenses)
        }
        Row() {
            CurrentBalance(income, expenses)
        }
    }
}

@Composable
fun IncomeAndExpensesPieChart(incomeAmount: Float, expenseAmount: Float) {
    val circleSize = 48.dp
    val incomeColor = MaterialTheme.colorScheme.primary
    val expensesColor = MaterialTheme.colorScheme.primaryContainer
    val backgroundColor = CardDefaults.cardColors().containerColor

    Column(modifier = Modifier.padding(8.dp)) {
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

                drawCircle(color = backgroundColor, radius = size.height - 90)
            }
        }
    }
}

fun calculateSweepAngle(sliceValue: Float, totalValue: Float): Float {
    return (sliceValue / totalValue) * 360f
}

@Composable
fun CurrentBalance(incomeAmount: MutableFloatState, expenseAmount: MutableFloatState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.balance),
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.secondary
        )

        Text(
            text = Util.formatCurrency(incomeAmount.floatValue - expenseAmount.floatValue),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun IncomeAndExpenses(incomeAmount: MutableFloatState, expenseAmount: MutableFloatState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(R.string.income),
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
        )

        Text(
            text = Util.formatCurrency(incomeAmount.floatValue),
            color = MaterialTheme.colorScheme.primary
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(R.string.expenses),
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
        )

        Text(
            text = Util.formatCurrency(expenseAmount.floatValue),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}