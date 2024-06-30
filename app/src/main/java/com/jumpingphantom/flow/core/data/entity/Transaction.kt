package com.jumpingphantom.flow.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val amount: Double,
    val category: TransactionCategory? = null,
    val description: String? = "",
    val date: LocalDateTime,
    val type: TransactionType
)

enum class TransactionType { INCOME, EXPENSE }

enum class TransactionCategory {
    // income types
    FREELANCE,
    REFUND,
    GIFTS,
    SALARY,
    AWARDS,
    // expense types
    TRANSPORTATION,
    GROCERIES,
    ENTERTAINMENT,
    DEBT_PAYMENTS,
    CHILDCARE,
    HEALTHCARE,
    TRAVEL,
    FOOD,
    HOUSING
}