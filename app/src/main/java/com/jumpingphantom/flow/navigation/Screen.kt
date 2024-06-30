package com.jumpingphantom.flow.navigation

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main-screen")
    data object StatisticsScreen : Screen("statistics-screen")
    data object IncomeHistoryScreen : Screen("income-screen")
    data object ExpenseHistoryScreen : Screen("expense-screen")
    data object HistoryScreen : Screen("history-screen")
    data object SettingsScreen : Screen("settings-screen")
}