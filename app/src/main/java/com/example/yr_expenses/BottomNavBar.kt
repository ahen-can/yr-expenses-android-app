package com.example.yr_expenses

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        NavItem("txn_list", "TXN LOG"),
        NavItem("add_txn", "ADD TXN"),
        NavItem("settings", "SETTINGS")
    )


    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {

        val navBackStackEntry = navController.currentBackStackEntryAsState()

        items.forEach { item ->

            val selected = navBackStackEntry.value?.destination?.route == item.route

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                label = {
                    Text(item.label, color = MaterialTheme.colorScheme.primary)
                },
                icon = {
                    Text("•", color = MaterialTheme.colorScheme.primary)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

data class NavItem(
    val route: String,
    val label: String
)
