package com.jumpingphantom.flow.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jumpingphantom.flow.R
import com.jumpingphantom.flow.features.history.ui.Expense.ExpenseHistory
import com.jumpingphantom.flow.features.history.ui.History
import com.jumpingphantom.flow.features.history.ui.Income.IncomeHistory
import com.jumpingphantom.flow.features.summary.ui.Summary
import com.jumpingphantom.flow.features.settings.ui.main.MainSettings
import com.jumpingphantom.flow.features.statistics.ui.Statistics

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                if (currentDestination != null) {
                    NavigationBarItem(
                        selected = currentDestination.route == Screen.MainScreen.route,
                        onClick = { navController.navigate(Screen.MainScreen.route) },
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text(text = "Home") }
                    )

                    NavigationBarItem(
                        selected = currentDestination.route == Screen.HistoryScreen.route,
                        onClick = { navController.navigate(Screen.HistoryScreen.route) },
                        icon = { Icon(painterResource(id = R.drawable.baseline_history_24), contentDescription = null) },
                        label = { Text(text = "History") }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.MainScreen.route,
            ) {
                composable(route = Screen.MainScreen.route) {
                    Root(
                        { navController.navigate(Screen.HistoryScreen.route) },
                        { navController.navigate(Screen.StatisticsScreen.route) },
                        { navController.navigate(Screen.IncomeHistoryScreen.route) },
                        { navController.navigate(Screen.ExpenseHistoryScreen.route) },
                        { navController.navigate(Screen.SettingsScreen.route) }
                    )
                }
                composable(route = Screen.SettingsScreen.route) { MainSettings() }
                composable(route = Screen.HistoryScreen.route) { History() }
                composable(route = Screen.StatisticsScreen.route) { Statistics() }
                composable(route = Screen.IncomeHistoryScreen.route) { IncomeHistory() }
                composable(route = Screen.ExpenseHistoryScreen.route) { ExpenseHistory() }
            }
        }
    }
}

@Composable
fun Root(
    onNavigateToHistory: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToIncome: () -> Unit,
    onNavigateToExpense: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Summary(
        onNavigateToHistory,
        onNavigateToStatistics,
        onNavigateToIncome,
        onNavigateToExpense,
    )
}