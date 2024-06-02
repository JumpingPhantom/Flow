package com.jumpingphantom.flow.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jumpingphantom.flow.data.entity.Transaction
import com.jumpingphantom.flow.data.repository.local.TransactionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TransactionViewmodel(private val repository: TransactionRepository) : ViewModel() {
    fun getTransactions() = viewModelScope.async {
        repository.getTransactions()
    }

    fun setTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.setTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        repository.deleteTransaction(transaction)
    }

    fun incomeSum() = viewModelScope.async {
        repository.incomeSum().sumOf { it.toDouble() }
    }

    fun expensesSum() = viewModelScope.async {
        repository.expensesSum().sumOf { it.toDouble() }
    }

//    fun printTransactions() = viewModelScope.launch {
//        Log.i("::DATABASE::", "${incomeSum().await()} - ${expensesSum().await()}")
//    }
}