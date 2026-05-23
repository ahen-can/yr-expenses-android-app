/*package com.example.yr_expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.yr_expenses.screens.*
import com.example.yr_expenses.ui.theme.TerminalGreen

sealed class Screen(val route: String, val label: String) {
    object TxnLog : Screen("txn_log", "TXN LOG")
    object AddTxn : Screen("add_txn", "ADD TXN")
    object Settings : Screen("settings", "SETTINGS")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(Screen.TxnLog, Screen.AddTxn, Screen.Settings)

    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = (navController.currentDestination?.route == screen.route),
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                        label = {
                            Text(screen.label, color = TerminalGreen)
                        },
                        icon = {},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TerminalGreen,
                            selectedTextColor = TerminalGreen,
                            unselectedIconColor = TerminalGreen.copy(alpha = 0.5f),
                            unselectedTextColor = TerminalGreen.copy(alpha = 0.5f),
                            indicatorColor = Color.Black
                        )
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.TxnLog.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.TxnLog.route) {
                TransactionListScreen(context = navController.context)
            }
            composable(Screen.AddTxn.route) {
                AddTransactionScreen(context = navController.context) {
                    navController.navigate(Screen.TxnLog.route)
                }
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}
*/

/*package com.example.yr_expenses

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.yr_expenses.screens.*
import com.example.yr_expenses.ui.theme.TerminalGreen

sealed class Screen(val route: String, val label: String) {
    object TxnLog : Screen("txn_log", "TXN LOG")
    object AddTxn : Screen("add_txn", "ADD TXN")
    object Settings : Screen("settings", "SETTINGS")
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val items = listOf(Screen.TxnLog, Screen.AddTxn, Screen.Settings)

    Scaffold(
        containerColor = Color.Black,

        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black
            ) {

                items.forEach { screen ->

                    NavigationBarItem(

                        selected = navController.currentBackStackEntryAsState().value?.destination?.route == screen.route,

                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },

                        label = {
                            Text(screen.label, color = TerminalGreen)
                        },

                        icon = {},

                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TerminalGreen,
                            selectedTextColor = TerminalGreen,
                            unselectedIconColor = TerminalGreen.copy(alpha = 0.5f),
                            unselectedTextColor = TerminalGreen.copy(alpha = 0.5f),
                            indicatorColor = Color.Black
                        )
                    )
                }
            }
        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.TxnLog.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.TxnLog.route) {
                TransactionListScreen()
            }

            composable(Screen.AddTxn.route) {
                AddTransactionScreen {
                    navController.navigate(Screen.TxnLog.route)
                }
            }

            composable(Screen.Settings.route) {
                SettingsScreen()
            }

        }
    }
}

 */

package com.example.yr_expenses

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.yr_expenses.screens.*
import com.example.yr_expenses.ui.theme.TerminalGreen
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────
// SCREENS
// ─────────────────────────────────────────────

sealed class Screen(
    val route: String,
    val label: String
) {

    object TxnLog : Screen(
        "txn_log",
        "TXN LOG"
    )

    object Analytics : Screen(
        "analytics",
        "ANALYTICS"
    )

    object AddTxn : Screen(
        "add_txn",
        "ADD TXN"
    )

    object Settings : Screen(
        "settings",
        "SETTINGS"
    )
}

// ─────────────────────────────────────────────
// NAVIGATION
// ─────────────────────────────────────────────

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val items = listOf(
        Screen.TxnLog,
        Screen.Analytics,
        Screen.AddTxn,
        Screen.Settings
    )

    Scaffold(

        containerColor = Color.Black,

        /*bottomBar = {

            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black
            ) {

                val navBackStackEntry by
                navController.currentBackStackEntryAsState()

                val currentRoute =
                    navBackStackEntry?.destination?.route

                items.forEach { screen ->

                    NavigationBarItem(

                        selected = currentRoute == screen.route,

                        onClick = {

                            navController.navigate(screen.route) {

                                popUpTo(
                                    navController.graph.findStartDestination().id
                                )

                                launchSingleTop = true
                            }

                        },

                        label = {

                            Text(
                                text = screen.label,
                                color = if (currentRoute == screen.route)
                                    TerminalGreen
                                else
                                    TerminalGreen.copy(alpha = 0.5f)
                            )

                        },

                        icon = {

                            Text(
                                text = "•",
                                color = if (currentRoute == screen.route)
                                    TerminalGreen
                                else
                                    TerminalGreen.copy(alpha = 0.5f)
                            )

                        },

                        colors = NavigationBarItemDefaults.colors(

                            selectedIconColor = TerminalGreen,

                            selectedTextColor = TerminalGreen,

                            unselectedIconColor =
                                TerminalGreen.copy(alpha = 0.5f),

                            unselectedTextColor =
                                TerminalGreen.copy(alpha = 0.5f),

                            indicatorColor = Color.Black
                        )
                    )
                }
            }
        }*/

        bottomBar = {

            val navBackStackEntry by
            navController.currentBackStackEntryAsState()

            val currentRoute =
                navBackStackEntry?.destination?.route

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),

                horizontalArrangement =
                    Arrangement.SpaceEvenly
            ) {

                items.forEach { screen ->

                    TextButton(

                        onClick = {

                            navController.navigate(screen.route) {

                                popUpTo(
                                    navController.graph.findStartDestination().id
                                )

                                launchSingleTop = true
                            }

                        }

                    ) {

                        Text(

                            text = screen.label,

                            color =
                                if (currentRoute == screen.route)
                                    TerminalGreen
                                else
                                    TerminalGreen.copy(alpha = 0.5f)

                        )

                    }

                }

            }

        }


    ) { paddingValues ->

        NavHost(

            navController = navController,

            startDestination = Screen.TxnLog.route,

            modifier = Modifier.padding(paddingValues)

        ) {

            // ─────────────────────────────
            // TXN LOG
            // ─────────────────────────────

            composable(Screen.TxnLog.route) {

                TransactionListScreen()

            }

            // ─────────────────────────────
            // ANALYTICS
            // ─────────────────────────────

            composable(Screen.Analytics.route) {

                AnalyticsScreen()

            }

            // ─────────────────────────────
            // ADD TXN
            // ─────────────────────────────

            composable(Screen.AddTxn.route) {

                AddTransactionScreen {

                    navController.navigate(
                        Screen.TxnLog.route
                    )

                }

            }

            // ─────────────────────────────
            // SETTINGS
            // ─────────────────────────────

            composable(Screen.Settings.route) {

                SettingsScreen()

            }

        }

    }

}
