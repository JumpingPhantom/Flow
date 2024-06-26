package com.jumpingphantom.flow.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jumpingphantom.flow.core.data.dao.TransactionDao
import com.jumpingphantom.flow.core.data.entity.Transaction

@Database(entities = [Transaction::class], version = 1)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun transactionDao() : TransactionDao
}