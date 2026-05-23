/*
package com.example.yr_expenses.screens


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.SmsParser
import com.example.yr_expenses.data.Transaction
import com.example.yr_expenses.ui.theme.TerminalGreen
import com.example.yr_expenses.data.CategoryStorage
@Composable
fun AddTransactionScreen(
    context: Context,
    onSaved: () -> Unit
) {
    var smsText by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var merchant by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("UPI") }
    var category by remember { mutableStateOf("Food") }

    //val categories = listOf("Food", "Groceries", "Transport", "Sports", "Beauty")
    var categories by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        categories = CategoryStorage.load(context)
    }


    Column(modifier = Modifier.padding(20.dp)) {

        Text("Add Transaction", color = Color.Green)

        Spacer(modifier = Modifier.height(20.dp))

        // Paste SMS
        OutlinedTextField(
            value = smsText,
            onValueChange = { smsText = it },
            label = { Text("Paste ICICI SMS") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val parsed = SmsParser.parse(smsText)
                if (parsed != null) {
                    amount = parsed.amount.toString()
                    merchant = parsed.merchant
                    mode = parsed.mode
                } else {
                    Toast.makeText(context, "Could not parse SMS", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Parse SMS")
        }

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (Rs)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = merchant,
            onValueChange = { merchant = it },
            label = { Text("Merchant") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = mode,
            onValueChange = { mode = it },
            label = { Text("Mode (UPI/Card)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // Dropdown category
        var expanded by remember { mutableStateOf(false) }

        Box {
            Button(onClick = { expanded = true }) {
                Text(category)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            category = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = {
                val txn = Transaction(
                    amount = amount.toFloatOrNull() ?: 0f,
                    merchant = merchant,
                    mode = mode,
                    timestamp = System.currentTimeMillis(),
                    raw = smsText.ifEmpty { "manual entry" },
                    category = category
                )

                LocalJsonStorage.save(context, txn)

                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()

                onSaved()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Save", color = Color.Black)
        }
    }
}
*/

package com.example.yr_expenses.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.SmsParser
import com.example.yr_expenses.data.CategoryStorage
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.data.Transaction

@Composable
fun AddTransactionScreen(
    onSaved: () -> Unit
) {

    val context = LocalContext.current

    var smsText by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var merchant by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("UPI") }
    var category by remember { mutableStateOf("Food") }

    var categories by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        categories = CategoryStorage.load(context)
    }

    Column(modifier = Modifier.padding(20.dp)) {

        Text("Add Transaction", color = Color.Green)

        Spacer(modifier = Modifier.height(20.dp))

        // Paste SMS
        OutlinedTextField(
            value = smsText,
            onValueChange = { smsText = it },
            label = { Text("Paste ICICI SMS") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val parsed = SmsParser.parse(smsText)

                if (parsed != null) {
                    amount = parsed.amount.toString()
                    merchant = parsed.merchant
                    mode = parsed.mode
                } else {
                    Toast.makeText(context, "Could not parse SMS", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Parse SMS")
        }

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount (Rs)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = merchant,
            onValueChange = { merchant = it },
            label = { Text("Merchant") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = mode,
            onValueChange = { mode = it },
            label = { Text("Mode (UPI/Card)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // Category dropdown
        var expanded by remember { mutableStateOf(false) }

        Box {

            Button(onClick = { expanded = true }) {
                Text(category)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                categories.forEach {

                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            category = it
                            expanded = false
                        }
                    )

                }

            }

        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = {

                val txn = Transaction(
                    amount = amount.toFloatOrNull() ?: 0f,
                    merchant = merchant,
                    mode = mode,
                    timestamp = System.currentTimeMillis(),
                    raw = smsText.ifEmpty { "manual entry" },
                    category = category
                )

                LocalJsonStorage.save(context, txn)

                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()

                onSaved()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Save", color = Color.Black)
        }

    }
}