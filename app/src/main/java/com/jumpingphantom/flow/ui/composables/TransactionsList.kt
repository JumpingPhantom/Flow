package com.jumpingphantom.flow.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jumpingphantom.flow.util.Util
import com.jumpingphantom.flow.viewmodel.TransactionViewmodel
import kotlinx.coroutines.launch

@Composable
fun TransactionsList(viewmodel: TransactionViewmodel) {
    val transactions by viewmodel.getTransactions().observeAsState(initial = emptyList())
    val listState = rememberLazyListState()

    LaunchedEffect(transactions.size) {
        if (transactions.isNotEmpty()) {
            launch {
                listState.animateScrollToItem(transactions.size - 1)
            }
        }
    }

    LazyColumn(state = listState, reverseLayout = true) {
        items(transactions) { transaction ->
            TransactionItem(transaction.isIncome, transaction.amount)
        }
    }
}

@Composable
fun TransactionItem(type: Boolean, amount: Float) {
    ListItem(
        headlineContent = {
            Text(text = if (type) "Income" else "Expense")
        },
        supportingContent = {
            Text(Util.formatCurrency(amount))
        },
        trailingContent = {
            Text("trailing")
        },
        modifier = Modifier.padding(8.dp)
    )
}
