package com.example.yr_expenses

import android.content.Context
import android.util.Log
import com.example.yr_expenses.data.LocalJsonStorage
import com.example.yr_expenses.data.Transaction
import org.json.JSONObject

object SmsParser {

    private val amountRegex = Regex("(?:Rs\\.?|INR)\\s?([\\d,]+\\.?\\d*)")
    private val merchantRegex = Regex(";\\s*([A-Za-z ]+) credited", RegexOption.IGNORE_CASE)
    private val upiRegex = Regex("UPI", RegexOption.IGNORE_CASE)

    fun handleMessage(context: Context, sender: String, body: String) {

        val txn = parse(body)
        if (txn == null) {
            Log.d("SMS_PARSED", "Failed parse: $body")
            return
        }

        // Log clean JSON
        Log.d("SMS_PARSED", JSONObject().apply {
            put("amount", txn.amount)
            put("merchant", txn.merchant)
            put("mode", txn.mode)
            put("timestamp", txn.timestamp)
            put("raw", txn.raw)
        }.toString())

        // 1️⃣ Send to Mint API
        ApiClient.send(context, JSONObject().apply {
            put("amount", txn.amount)
            put("merchant", txn.merchant)
            put("mode", txn.mode)
            put("timestamp", txn.timestamp)
            put("raw", txn.raw)
        })

        // 2️⃣ Save to local storage
        LocalJsonStorage.save(context, txn)
    }

    fun parse(body: String): Transaction? {

        if (!body.contains("debited", ignoreCase = true))
            return null

        val amount = amountRegex.find(body)
            ?.groupValues?.get(1)
            ?.replace(",", "")
            ?.toFloatOrNull()
            ?: return null

        val merchant = merchantRegex.find(body)
            ?.groupValues?.get(1)
            ?.trim()
            ?: "Unknown"

        val mode = if (upiRegex.containsMatchIn(body)) "UPI" else "Other"

        return Transaction(
            amount = amount,
            merchant = merchant,
            mode = mode,
            timestamp = System.currentTimeMillis(),
            raw = body,
            category = "Uncategorized"
        )
    }
}
