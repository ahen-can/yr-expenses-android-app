package com.example.yr_expenses.data

data class Transaction(
    val amount: Float,
    val merchant: String,
    val mode: String,
    val timestamp: Long,
    val raw: String,
    var category: String = "Uncategorized"
)
