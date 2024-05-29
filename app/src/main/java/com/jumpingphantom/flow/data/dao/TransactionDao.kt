package com.jumpingphantom.flow.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jumpingphantom.flow.data.entity.Transaction

@Dao
interface TransactionDao {
    @Insert fun insert(transaction: Transaction)
    @Delete fun delete(transaction: Transaction)
    @Query("SELECT * from transactions") fun getAll() : List<Transaction>
}