package com.jumpingphantom.flow.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val amount: Float,
    val category: String? = "",
    val description: String? = "",
    val date: LocalDate,
    val isIncome: Boolean
)