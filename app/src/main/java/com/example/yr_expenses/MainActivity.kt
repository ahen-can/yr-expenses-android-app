/*
package com.example.yr_expenses

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.material3.*

class MainActivity : ComponentActivity() {

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            Log.d("PERMISSION", "Permissions results = $permissions")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request SMS permissions
        requestPermission.launch(
            arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.INTERNET
            )
        )

        setContent {

            // Track which screen we are on
            var screen by remember { mutableStateOf("list") }

            MaterialTheme {

                when (screen) {
                    "list" -> TransactionListScreen(
                        context = this,
                        onAddClicked = { screen = "add" }   // 👈 navigate to add screen
                    )

                    "add" -> AddTransactionScreen(
                        context = this,
                        onSaved = { screen = "list" }       // 👈 go back to list after saving
                    )
                }
            }
        }
    }
}

*/
package com.example.yr_expenses

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.yr_expenses.ui.theme.TerminalTheme

class MainActivity : ComponentActivity() {

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            Log.d("PERMISSION", "Permissions: $perms")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions.launch(
            arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.INTERNET
            )
        )

        setContent {
            TerminalTheme {
                AppNavigation()
            }
        }
    }
}
