package com.jumpingphantom.flow.features.summary.ui

import com.jumpingphantom.flow.core.data.entity.Transaction

data class UiState(val income: Double, val expense: Double, val recent: List<Transaction>)
