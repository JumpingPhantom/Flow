package com.jumpingphantom.flow.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jumpingphantom.flow.data.entity.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)
    @Delete
    suspend fun delete(transaction: Transaction)
    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<Transaction>

    @Query("SELECT amount FROM transactions WHERE isIncome = 1")
    suspend fun getIncome(): List<Float>

    @Query("SELECT amount FROM transactions WHERE isIncome = 0")
    suspend fun getExpenses(): List<Float>
}