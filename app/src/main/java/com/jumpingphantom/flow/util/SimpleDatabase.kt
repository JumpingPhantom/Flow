package com.jumpingphantom.flow.util

import android.content.Context
import androidx.room.Room
import com.jumpingphantom.flow.data.ApplicationDatabase

object SimpleDatabase {
    fun getInstance(context: Context): ApplicationDatabase {
        return Room.databaseBuilder(context, ApplicationDatabase::class.java, "transactions_db")
            .build()
    }
}