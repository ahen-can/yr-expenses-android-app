package com.example.yr_expenses

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object SmsParser {

    private val amountRegex = Regex("(?:Rs\\.?|INR)\\s?([\\d,]+\\.?\\d*)")
    private val merchantRegex = Regex(";\\s*([A-Za-z ]+) credited", RegexOption.IGNORE_CASE)
    private val upiRegex = Regex("UPI", RegexOption.IGNORE_CASE)

    fun handleMessage(context: Context, sender: String, body: String) {

        if (!body.contains("debited", ignoreCase = true)) return

        val amount = amountRegex.find(body)
            ?.groupValues?.get(1)
            ?.replace(",", "")
            ?.toFloatOrNull()

        val merchant = merchantRegex.find(body)
            ?.groupValues?.get(1)
            ?.trim()
            ?: "Unknown"

        val mode = if (upiRegex.containsMatchIn(body)) "UPI" else "Other"

        if (amount != null) {

            val timestamp = System.currentTimeMillis()

            val json = JSONObject().apply {
                put("amount", amount)
                put("merchant", merchant)
                put("mode", mode)
                put("timestamp", timestamp)
                put("raw", body)
            }

            Log.d("SMS_PARSED", json.toString())

            // 1️⃣ Send to Mint
            ApiClient.send(context, json)

            // 2️⃣ Save locally to JSON
            LocalJsonStorage.save(context, json)

        } else {
            Log.d("SMS_PARSED", "Failed parse: $body")
        }
    }
}
