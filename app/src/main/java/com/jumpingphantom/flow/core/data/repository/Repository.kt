package com.jumpingphantom.flow.core.data.repository

import com.jumpingphantom.flow.core.data.entity.Transaction
import kotlinx.coroutines.flow.Flow


interface Repository {
    val transactions: Flow<List<Transaction>>
    suspend fun add(transaction: Transaction) : Unit
    suspend fun delete(transaction: Transaction) : Unit
    fun fetchAll() : Flow<List<Transaction>>
}