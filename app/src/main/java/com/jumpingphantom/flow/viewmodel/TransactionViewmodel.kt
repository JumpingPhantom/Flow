package com.jumpingphantom.flow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jumpingphantom.flow.data.entity.Transaction
import com.jumpingphantom.flow.data.repository.local.TransactionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TransactionViewmodel(private val repository: TransactionRepository) : ViewModel() {
    var incomeTotal = MutableLiveData(0f)
    var expensesTotal = MutableLiveData(0f)

    init {
        viewModelScope.launch {
            incomeTotal.value = incomeSum().await().toFloat()
            expensesTotal.value = expensesSum().await().toFloat()
        }
    }

    fun getTransactions(): LiveData<List<Transaction>> = repository.getTransactions()

    fun setTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.setTransaction(transaction)
        updateIncomeAndExpenses()
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.deleteTransaction(transaction)
        updateIncomeAndExpenses()
    }

    private fun incomeSum() = viewModelScope.async {
        repository.incomeSum().sumOf { it.toDouble() }
    }

    private fun expensesSum() = viewModelScope.async {
        repository.expensesSum().sumOf { it.toDouble() }
    }

    fun updateIncomeAndExpenses() = viewModelScope.launch {
        incomeTotal.value = incomeSum().await().toFloat()
        expensesTotal.value = expensesSum().await().toFloat()
    }
}