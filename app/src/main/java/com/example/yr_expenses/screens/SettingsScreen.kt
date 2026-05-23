/*package com.example.yr_expenses.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.ui.theme.TerminalGreen

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp)
    ) {
        Text("Settings", color = TerminalGreen)
        Spacer(Modifier.height(20.dp))
        Text("Coming soon...", color = TerminalGreen.copy(alpha = 0.7f))
    }
}

*/

/*
package com.example.yr_expenses.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.data.CategoryStorage
import com.example.yr_expenses.ui.theme.TerminalGreen

@Composable
fun SettingsScreen(context: Context) {

    var categories by remember { mutableStateOf(listOf<String>()) }
    var newCategory by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        categories = CategoryStorage.load(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Text(
            "CATEGORY MANAGEMENT",
            color = TerminalGreen,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = newCategory,
            onValueChange = { newCategory = it },
            label = { Text("New Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                if (newCategory.isNotBlank()) {

                    CategoryStorage.add(context, newCategory.trim())

                    categories = CategoryStorage.load(context)

                    newCategory = ""
                }
            }
        ) {
            Text("Add Category")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Existing Categories",
            color = TerminalGreen
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(categories) { cat ->

                Text(
                    cat,
                    color = TerminalGreen,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

*/

/*
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
import com.example.yr_expenses.data.CategoryStorage
import com.example.yr_expenses.ui.theme.TerminalGreen

@Composable
fun SettingsScreen() {

    val context = LocalContext.current

    var categories by remember { mutableStateOf(listOf<String>()) }
    var newCategory by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        categories = CategoryStorage.load(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Text(
            text = "CATEGORY MANAGEMENT",
            color = TerminalGreen,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = newCategory,
            onValueChange = { newCategory = it },
            label = { Text("New Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                if (newCategory.isNotBlank()) {

                    CategoryStorage.add(context, newCategory.trim())

                    categories = CategoryStorage.load(context)

                    newCategory = ""
                }
            }
        ) {
            Text("Add Category")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Existing Categories",
            color = TerminalGreen
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(categories) { cat ->

                Text(
                    text = cat,
                    color = TerminalGreen,
                    modifier = Modifier.padding(6.dp)
                )

            }

        }
    }
}
*/

package com.example.yr_expenses.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.yr_expenses.ui.theme.CategoryColors
import com.example.yr_expenses.data.CategoryStorage
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.ui.theme.TerminalGreen

@Composable
fun SettingsScreen() {

    val context = LocalContext.current

    var categories by remember { mutableStateOf(listOf<String>()) }

    var newCategory by remember { mutableStateOf("") }

    var categoryToDelete by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        categories = CategoryStorage.load(context)
    }

    // ─────────────────────────────────────────────
    // DELETE CONFIRMATION DIALOG
    // ─────────────────────────────────────────────

    if (categoryToDelete != null) {

        AlertDialog(

            onDismissRequest = {
                categoryToDelete = null
            },

            title = {
                Text("Delete Category?")
            },

            text = {
                Text(
                    "Transactions using \"$categoryToDelete\" will become Uncategorized."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        val txns = LocalJsonStorage.load(context)

                        val updated = txns.map {

                            if (it.category == categoryToDelete)
                                it.copy(category = "Uncategorized")
                            else it
                        }

                        LocalJsonStorage.update(context, updated)

                        CategoryStorage.remove(
                            context,
                            categoryToDelete!!
                        )

                        categories = CategoryStorage.load(context)

                        categoryToDelete = null
                    }
                ) {
                    Text("Delete")
                }

            },

            dismissButton = {

                TextButton(
                    onClick = {
                        categoryToDelete = null
                    }
                ) {
                    Text("Cancel")
                }

            }

        )

    }

    // ─────────────────────────────────────────────
    // MAIN UI
    // ─────────────────────────────────────────────

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {

        Text(
            text = "CATEGORY MANAGEMENT",
            color = TerminalGreen,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = newCategory,
            onValueChange = { newCategory = it },
            label = { Text("New Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                if (newCategory.isNotBlank()) {

                    CategoryStorage.add(
                        context,
                        newCategory.trim()
                    )

                    categories = CategoryStorage.load(context)

                    newCategory = ""
                }
            }
        ) {
            Text("Add Category")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Existing Categories",
            color = TerminalGreen
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(categories) { cat ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = cat,
                        color = CategoryColors.getColor(cat)
                    )

                    // Protect Uncategorized
                    if (cat != "Uncategorized") {

                        IconButton(
                            onClick = {
                                categoryToDelete = cat
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )

                        }

                    }

                }

            }

        }

    }

}