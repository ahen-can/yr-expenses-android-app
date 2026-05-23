/*
package com.example.yr_expenses.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.clickable
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.data.Transaction

@Composable
fun TransactionListScreen(
    context: Context,
    onAddClicked: () -> Unit
) {
    val txns = remember { mutableStateListOf<Transaction>() }

    // Load data from JSON
    LaunchedEffect(Unit) {
        txns.clear()
        txns.addAll(LocalJsonStorage.load(context))
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        // ───────────── Title ─────────────
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "YR EXPENSES LOG",
                color = Color(0xFF00FF00),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ───────────── List ─────────────
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(txns.size) { i ->
                val t = txns[i]
                TransactionRow(t)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ───────────── Add Button ─────────────
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "[ + ADD TRANSACTION ]",
                color = Color(0xFF00FF00),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onAddClicked() }
            )
        }
    }
}

@Composable
fun TransactionRow(t: Transaction) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale.getDefault()) }
    val timeText = dateFormat.format(Date(t.timestamp))
    val cleanAmount = if (t.amount % 1f == 0f) t.amount.toInt() else t.amount

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text("₹ $cleanAmount — ${t.merchant}", color = Color(0xFF00FF00))
        Text("${t.mode} • $timeText", color = Color(0xFF00FF00).copy(alpha = 0.7f))
        Text("Category: ${t.category}", color = Color(0xFF00FF00).copy(alpha = 0.7f))
    }
}
*/

/*package com.example.yr_expenses.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.data.Transaction
import com.example.yr_expenses.ui.theme.TerminalGreen
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap
import com.example.yr_expenses.data.CategoryStorage

@Composable
fun TransactionListScreen(context: Context) {
    var txns by remember { mutableStateOf(emptyList<Transaction>()) }

    LaunchedEffect(Unit) {
        val loaded = LocalJsonStorage.load(context)
        txns = loaded.sortedByDescending { it.timestamp }
    }

    val grouped = txns.groupByDay()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
    ) {
        items(grouped.keys.toList()) { day ->

            var expanded by remember { mutableStateOf(grouped.keys.indexOf(day) < 3) }

            // Day header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = day,
                    color = TerminalGreen,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (expanded) "▲" else "▼",
                    color = TerminalGreen
                )
            }

            if (expanded) {
                grouped[day]?.forEach { t ->
                    TransactionRow(
                        t = t,
                        onCategoryChanged = { newCat ->
                            t.category = newCat
                            LocalJsonStorage.update(context, txns)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionRow(t: Transaction, onCategoryChanged: (String) -> Unit) {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val timeText = dateFormat.format(Date(t.timestamp))

    var categoryMenu by remember { mutableStateOf(false) }

    /*
    val categories = listOf(
        "Food", "Groceries", "Transport", "Sports", "Beauty", "Misc", "Uncategorized"
    )
*/
    var categories by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        categories = CategoryStorage.load(context)
    }

    val categoryColors = mapOf(
        "Food" to Color.Green,
        "Groceries" to Color.Yellow,
        "Transport" to Color.Cyan,
        "Sports" to Color.Magenta,
        "Beauty" to Color.Blue,
        "Misc" to Color.Gray,
        "Uncategorized" to Color.Red
    )

    Column(modifier = Modifier.padding(vertical = 8.dp)) {

        Text(
            text = "₹ ${t.amount.toInt()} — ${t.merchant}",
            color = TerminalGreen
        )

        Text(
            text = "${t.mode} • $timeText",
            color = TerminalGreen.copy(alpha = 0.6f)
        )

        Box {
            Text(
                text = "Category: ${t.category}",
                color = categoryColors[t.category] ?: TerminalGreen,
                modifier = Modifier.clickable { categoryMenu = true }
            )

            DropdownMenu(
                expanded = categoryMenu,
                onDismissRequest = { categoryMenu = false },
                containerColor = Color.Black
            ) {
                categories.forEach { c ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                c,
                                color = categoryColors[c] ?: TerminalGreen
                            )
                        },
                        onClick = {
                            onCategoryChanged(c)
                            categoryMenu = false
                        }
                    )
                }
            }
        }
    }
}

// GROUP BY DAY
fun List<Transaction>.groupByDay(): LinkedHashMap<String, List<Transaction>> {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val result = LinkedHashMap<String, List<Transaction>>()

    this.forEach { txn ->
        val day = formatter.format(Date(txn.timestamp))
        result[day] = (result[day] ?: emptyList()) + txn
    }

    return result
}
*/

package com.example.yr_expenses.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.data.Transaction
import com.example.yr_expenses.data.CategoryStorage
import com.example.yr_expenses.ui.theme.TerminalGreen
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap
import com.example.yr_expenses.ui.theme.CategoryColors

@Composable
fun TransactionListScreen() {

    val context = LocalContext.current

    var txns by remember { mutableStateOf(emptyList<Transaction>()) }

    // Load JSON transactions
    LaunchedEffect(Unit) {
        val loaded = LocalJsonStorage.load(context)
        txns = loaded.sortedByDescending { it.timestamp }
    }

    val grouped = txns.groupByDay()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
    ) {

        items(grouped.keys.toList()) { day ->

            var expanded by remember { mutableStateOf(grouped.keys.indexOf(day) < 3) }

            // ───────────── Day Header ─────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(vertical = 8.dp)
            ) {

                Text(
                    text = day,
                    color = TerminalGreen,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = if (expanded) "▲" else "▼",
                    color = TerminalGreen
                )
            }

            // ───────────── Transactions ─────────────
            if (expanded) {

                grouped[day]?.forEach { t ->

                    TransactionRow(
                        t = t,
                        onCategoryChanged = { newCat ->

                            val updated = txns.map {
                                if (it.timestamp == t.timestamp)
                                    it.copy(category = newCat)
                                else it
                            }

                            txns = updated
                            LocalJsonStorage.update(context, updated)
                        }
                    )

                }

            }

        }

    }

}

@Composable
fun TransactionRow(
    t: Transaction,
    onCategoryChanged: (String) -> Unit
) {

    val context = LocalContext.current

    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val timeText = dateFormat.format(Date(t.timestamp))

    var categoryMenu by remember { mutableStateOf(false) }

    var categories by remember { mutableStateOf(listOf<String>()) }

    // Load categories from storage
    LaunchedEffect(Unit) {
        categories = CategoryStorage.load(context)
    }

/*    val categoryColors = mapOf(
        "Food" to Color.Green,
        "Groceries" to Color.Yellow,
        "Transport" to Color.Cyan,
        "Sports" to Color.Magenta,
        "Beauty" to Color.Blue,
        "Misc" to Color.Gray,
        "Uncategorized" to Color.Red
    )
*/



    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {

        Text(
            text = "₹ ${t.amount.toInt()} — ${t.merchant}",
            color = TerminalGreen
        )

        Text(
            text = "${t.mode} • $timeText",
            color = TerminalGreen.copy(alpha = 0.6f)
        )

        Box {

            Text(
                text = "Category: ${t.category}",
                color = CategoryColors.getColor(t.category),
                modifier = Modifier.clickable { categoryMenu = true }
            )

            DropdownMenu(
                expanded = categoryMenu,
                onDismissRequest = { categoryMenu = false },
                containerColor = Color.Black
            ) {

                categories.forEach { c ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                c,
                                color = CategoryColors.getColor(t.category),
                            )
                        },
                        onClick = {
                            onCategoryChanged(c)
                            categoryMenu = false
                        }
                    )

                }

            }

        }

    }

}

// ─────────────────────────────────────────────
// GROUP TRANSACTIONS BY DAY
// ─────────────────────────────────────────────

fun List<Transaction>.groupByDay(): LinkedHashMap<String, List<Transaction>> {

    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val result = LinkedHashMap<String, List<Transaction>>()

    this.forEach { txn ->

        val day = formatter.format(Date(txn.timestamp))

        result[day] = (result[day] ?: emptyList()) + txn

    }

    return result

}