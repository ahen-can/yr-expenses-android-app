package com.example.yr_expenses

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionListScreen(context: Context) {

    var list by remember { mutableStateOf(emptyList<JSONObject>()) }

    // Load on first display
    LaunchedEffect(Unit) {
        list = LocalStorage.loadAll(context).reversed() // newest first
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp)) // title moved down

        // Centered Title
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "yr expenses log",
                color = Color(0xFF00FF41),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(list) { obj ->
                TransactionRow(obj)
            }
        }
    }
}


@Composable
fun TransactionRow(obj: JSONObject) {

    val amt = obj.getDouble("amount")

    // Remove decimal for clean display
    val amountClean = if (amt % 1 == 0.0) {
        amt.toInt().toString()   // 20 instead of 20.0
    } else {
        amt.toString()
    }

    // Format timestamp
    val timestamp = obj.getLong("timestamp")
    val dateFormatted = formatTimestamp(timestamp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Text(
            text = "₹$amountClean — ${obj.getString("merchant")}",
            color = Color(0xFF00FF41),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = obj.getString("mode"),
            color = Color(0xFF00FF41),
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = dateFormatted,
            color = Color(0xFF00FF41),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}


// Helper function to convert timestamp → human readable
fun formatTimestamp(ms: Long): String {
    return try {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        sdf.format(Date(ms))
    } catch (e: Exception) {
        ms.toString()
    }
}
