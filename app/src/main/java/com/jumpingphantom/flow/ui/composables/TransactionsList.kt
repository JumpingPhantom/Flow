package com.jumpingphantom.flow.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jumpingphantom.flow.data.entity.Transaction
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
        items(transactions, key = { t -> t.uid }) { transaction ->
            TransactionItem(transaction, viewmodel)
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction, viewmodel: TransactionViewmodel) {
    val type = transaction.isIncome
    val date = transaction.date.toString()
    val amount = transaction.amount
    val color = if (type) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    SwipeToDismissContainer(
        item = transaction,
        onDelete = { viewmodel.deleteTransaction(transaction) }) {

        ListItem(
            headlineContent = {
                Text(text = if (type) "Income" else "Expense", color = color)
            },
            supportingContent = {
                Text(Util.formatCurrency(amount))
            },
            trailingContent = {
                Text(date)
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDismissContainer(item: T, onDelete: (T) -> Unit, content: @Composable (T) -> Unit) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(confirmValueChange = { value ->
        if (value == SwipeToDismissBoxValue.StartToEnd) {
            isRemoved = true
            true
        } else {
            false
        }
    })

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) onDelete(item)
    }

    SwipeToDismissBox(state = state, backgroundContent = {
        val col by animateColorAsState(
            targetValue = when (state.targetValue) {
                SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.background
                SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.error
                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
            }, label = "dismiss_color_animation"
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(col)
                .fillMaxSize()
        ) {}
    }, content = { content(item) })
}

