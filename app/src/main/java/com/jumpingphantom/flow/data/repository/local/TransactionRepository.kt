package com.jumpingphantom.flow.data.repository.local

import com.jumpingphantom.flow.data.dao.TransactionDao
import com.jumpingphantom.flow.data.entity.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    fun getTransactions() = transactionDao.getAll()
    suspend fun setTransaction(transaction: Transaction) = transactionDao.insert(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.delete(transaction)
    suspend fun incomeSum() = transactionDao.getIncome()
    suspend fun expensesSum() = transactionDao.getExpenses()
}