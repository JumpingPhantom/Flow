package com.jumpingphantom.flow.util

import android.content.Context
import androidx.room.Room
import com.jumpingphantom.flow.data.ApplicationDatabase

object SimpleDatabase {
    private val instance: ApplicationDatabase? = null
    fun getInstance(context: Context): ApplicationDatabase {
        return instance ?: Room.databaseBuilder(context, ApplicationDatabase::class.java, "transactions_db")
            .build()
    }
}