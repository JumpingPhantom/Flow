package com.jumpingphantom.flow.core.data.repository.local

import com.jumpingphantom.flow.core.data.dao.TransactionDao
import com.jumpingphantom.flow.core.data.entity.Transaction
import com.jumpingphantom.flow.core.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val transactionDao: TransactionDao) : Repository {
    override val transactions: Flow<List<Transaction>> by lazy { fetchAll() }

    override suspend fun add(transaction: Transaction) {
        try {
            transactionDao.add(transaction)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun delete(transaction: Transaction) {
        try {
            transactionDao.delete(transaction)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun fetchAll() = transactionDao.fetchAll()
}