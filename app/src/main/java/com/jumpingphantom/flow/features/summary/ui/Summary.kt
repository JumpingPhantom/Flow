package com.jumpingphantom.flow.features.summary.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jumpingphantom.flow.core.data.entity.TransactionType
import com.jumpingphantom.flow.core.util.formatCurrency
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@Composable
fun Summary(
    onNavigateToHistory: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToIncome: () -> Unit,
    onNavigateToExpense: () -> Unit,
) {
    val summaryViewModel = koinViewModel<SummaryViewModel>()

    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            BalanceCard(onNavigateToStatistics, summaryViewModel.uiState)
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            IncomeCard(
                modifier = Modifier.weight(1f),
                { onNavigateToIncome() },
                summaryViewModel.uiState
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            ExpenseCard(
                modifier = Modifier.weight(1f),
                { onNavigateToExpense() },
                summaryViewModel.uiState
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            RecentTransactions(onNavigateToHistory, summaryViewModel.uiState)
        }
    }
}

@Composable
fun RecentTransactions(onNavigateToHistory: () -> Unit, uiState: UiState) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text("Recent transactions")
            Text(
                text = "view all",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onNavigateToHistory() }
            )
        }

        Row {
            LazyColumn {
                items(uiState.recent.takeLast(4)) {
                    ItemRow(it.amount, it.type, it.date)
                }
            }
        }
    }
}

@Composable
fun ExpenseCard(modifier: Modifier = Modifier, onNavigateToExpense: () -> Unit, uiState: UiState) {
    Card(onClick = { onNavigateToExpense() }, modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Expense", style = MaterialTheme.typography.labelMedium)
            Text(
                text = formatCurrency(uiState.expense),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun IncomeCard(modifier: Modifier = Modifier, onNavigateToIncome: () -> Unit, uiState: UiState) {
    Card(onClick = { onNavigateToIncome() }, modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Income", style = MaterialTheme.typography.labelMedium)
            Text(
                text = formatCurrency(uiState.income),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun BalanceCard(onNavigateToStatistics: () -> Unit, uiState: UiState) {
    OutlinedCard(
        onClick = { onNavigateToStatistics() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Balance", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = formatCurrency(uiState.income + uiState.expense),
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun ItemRow(amount: Double, type: TransactionType, date: LocalDateTime) {
    OutlinedCard(modifier = Modifier.padding(vertical = 4.dp)) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            formatCurrency(amount),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(Modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        when (type) {
                            TransactionType.INCOME -> Text("income")
                            TransactionType.EXPENSE -> Text("expense")
                        }
                        Spacer(modifier = Modifier.weight(1f, true))
                        Text(
                            "${date.year} / ${date.monthValue} / ${date.dayOfMonth}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}