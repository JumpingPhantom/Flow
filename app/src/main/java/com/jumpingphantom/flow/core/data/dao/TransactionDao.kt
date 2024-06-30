package com.jumpingphantom.flow.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jumpingphantom.flow.core.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun add(transaction: Transaction)
    @Delete
    suspend fun delete(transaction: Transaction)
    @Query("SELECT * FROM transactions")
    fun fetchAll(): Flow<List<Transaction>>
}