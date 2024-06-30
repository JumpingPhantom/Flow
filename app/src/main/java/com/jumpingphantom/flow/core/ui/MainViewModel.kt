package com.jumpingphantom.flow.core.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jumpingphantom.flow.core.data.entity.Transaction
import com.jumpingphantom.flow.core.data.repository.local.LocalDataSource
import kotlinx.coroutines.launch

class MainViewModel(private val localDataSource: LocalDataSource) : ViewModel() {
    fun add(transaction: Transaction) = viewModelScope.launch {
        localDataSource.add(transaction)
    }

    fun delete(transaction: Transaction) = viewModelScope.launch {
        localDataSource.delete(transaction)
    }

    fun logSomething() {
        Log.i("::LOG::", "MAIN VIEWMODEL WORKS,")
    }
}