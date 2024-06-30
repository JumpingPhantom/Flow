package com.jumpingphantom.flow.features.summary.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jumpingphantom.flow.core.data.entity.Transaction
import com.jumpingphantom.flow.core.data.entity.TransactionType
import com.jumpingphantom.flow.core.data.repository.local.LocalDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class SummaryViewModel(private val dataSource: LocalDataSource) : ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions

    var uiState by mutableStateOf(UiState(0.0, 0.0, transactions.value))
        private set

    init {
        viewModelScope.launch {
            dataSource.transactions.collect {
                _transactions.value = it
                updateState()
            }
        }
    }

    fun addTransaction(transaction: Transaction) = viewModelScope.launch {
        dataSource.add(transaction)
        updateState()
    }

    private fun updateState() {
        val income =
            _transactions.value.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }

        val expense =
            -transactions.value.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

        uiState = uiState.copy(
            income = income,
            expense = expense,
            recent = _transactions.value
        )
    }
}