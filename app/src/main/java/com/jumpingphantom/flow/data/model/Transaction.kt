package com.jumpingphantom.flow.data.model

import java.time.LocalDate

data class Transaction(
    val amount: Float,
    val category: String,
    val description: String,
    val date: LocalDate,
    val isIncome: Boolean
)