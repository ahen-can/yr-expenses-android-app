package com.example.yr_expenses.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.ui.theme.CategoryColors
import com.example.yr_expenses.data.AnalyticsEngine
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.data.Transaction
import com.example.yr_expenses.ui.theme.TerminalGreen

@Composable
fun AnalyticsScreen() {

    val context = LocalContext.current

    var txns by remember {
        mutableStateOf(emptyList<Transaction>())
    }

    LaunchedEffect(Unit) {
        txns = LocalJsonStorage.load(context)
    }

    // ─────────────────────────────────────────────
    // MONTH FILTER
    // ─────────────────────────────────────────────

    val months = AnalyticsEngine.availableMonths(txns)

    var selectedMonth by remember {
        mutableStateOf("Till Now")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    // ─────────────────────────────────────────────
    // KPI VALUES
    // ─────────────────────────────────────────────

    val totalSpend =
        AnalyticsEngine.totalSpend(txns, selectedMonth)

    val txnCount =
        AnalyticsEngine.transactionCount(txns, selectedMonth)

    val topCategory =
        AnalyticsEngine.topCategory(txns, selectedMonth)

    val topMerchant =
        AnalyticsEngine.topMerchant(txns, selectedMonth)

    val categoryBreakdown =
        AnalyticsEngine.categoryBreakdown(txns, selectedMonth)

    val monthlyTotals =
        AnalyticsEngine.monthlyTotals(txns)

    // ─────────────────────────────────────────────
    // UI
    // ─────────────────────────────────────────────

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "ANALYTICS",
                color = TerminalGreen,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ─────────────────────────────
            // Month Dropdown
            // ─────────────────────────────

            Box {

                Button(
                    onClick = {
                        expanded = true
                    }
                ) {
                    Text(selectedMonth)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    months.forEach { month ->

                        DropdownMenuItem(
                            text = {
                                Text(month)
                            },
                            onClick = {
                                selectedMonth = month
                                expanded = false
                            }
                        )

                    }

                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            // ─────────────────────────────
            // KPI Cards
            // ─────────────────────────────

            AnalyticsCard(
                title = "Total Spend",
                value = "₹ $totalSpend"
            )

            AnalyticsCard(
                title = "Transactions",
                value = txnCount.toString()
            )

            AnalyticsCard(
                title = "Top Category",
                value = "${topCategory.first} • ₹ ${topCategory.second}"
            )

            AnalyticsCard(
                title = "Top Merchant",
                value = "${topMerchant.first} • ₹ ${topMerchant.second}"
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "CATEGORY BREAKDOWN",
                color = TerminalGreen,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(14.dp))
        }

        // ─────────────────────────────────────────
        // CATEGORY BARS
        // ─────────────────────────────────────────

        items(categoryBreakdown) { pair ->

            val category = pair.first
            val amount = pair.second

            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {

                Text(
                    text = "$category • ₹ $amount",
                    color = CategoryColors.getColor(category)
                )

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = {
                        if (totalSpend == 0) 0f
                        else amount.toFloat() / totalSpend.toFloat()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    color = CategoryColors.getColor(category),
                    trackColor = Color.DarkGray
                )

            }

        }

        item {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "MONTHLY TREND",
                color = TerminalGreen,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

        }

        items(monthlyTotals) { pair ->

            val month = pair.first
            val total = pair.second

            Column(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {

                Text(
                    text = "$month • ₹ $total",
                    color = TerminalGreen
                )

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = {

                        val max =
                            monthlyTotals.maxOfOrNull { it.second }
                                ?: 1

                        total.toFloat() / max.toFloat()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    color = TerminalGreen,
                    trackColor = Color.DarkGray
                )

            }

        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }

    }

}

@Composable
fun AnalyticsCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = TerminalGreen.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = TerminalGreen,
                style = MaterialTheme.typography.titleLarge
            )

        }

    }

}