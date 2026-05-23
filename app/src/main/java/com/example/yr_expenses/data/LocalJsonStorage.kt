/*

package com.example.yr_expenses.data

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object LocalJsonStorage {

    private const val FILE_NAME = "transactions.json"

    // Convert JSONObject → Transaction
    private fun jsonToTransaction(obj: JSONObject): Transaction {
        return Transaction(
            amount = obj.getDouble("amount").toFloat(),
            merchant = obj.getString("merchant"),
            mode = obj.getString("mode"),
            timestamp = obj.getLong("timestamp"),
            raw = obj.getString("raw"),
            category = obj.optString("category", "Uncategorized")
        )
    }

    // Convert Transaction → JSONObject
    private fun transactionToJson(t: Transaction): JSONObject {
        return JSONObject().apply {
            put("amount", t.amount)
            put("merchant", t.merchant)
            put("mode", t.mode)
            put("timestamp", t.timestamp)
            put("raw", t.raw)
            put("category", t.category)
        }
    }

    // ─────────────────────────────────────────────
    // LOAD: return List<Transaction>
    // ─────────────────────────────────────────────
    fun load(context: Context): List<Transaction> {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return emptyList()

            val text = file.readText()
            val arr = JSONArray(text)

            (0 until arr.length()).map { i ->
                jsonToTransaction(arr.getJSONObject(i))
            }

        } catch (e: Exception) {
            Log.e("LOCAL_JSON", "Load error: ${e.message}")
            emptyList()
        }
    }

    // ─────────────────────────────────────────────
    // SAVE: append Transaction
    // ─────────────────────────────────────────────
    fun save(context: Context, t: Transaction) {
        try {
            val file = File(context.filesDir, FILE_NAME)

            val arr = if (file.exists()) {
                JSONArray(file.readText())
            } else {
                JSONArray()
            }

            arr.put(transactionToJson(t))

            file.writeText(arr.toString())

            Log.d("LOCAL_JSON", "Saved locally: $t")

        } catch (e: Exception) {
            Log.e("LOCAL_JSON", "Save error: ${e.message}")
        }
    }

    // Optional: clear all
    fun clear(context: Context) {
        File(context.filesDir, FILE_NAME).delete()
    }
}

*/

package com.example.yr_expenses.data

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object LocalJsonStorage {

    private const val FILE_NAME = "transactions.json"

    fun load(context: Context): List<Transaction> {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return emptyList()

            val arr = JSONArray(file.readText())
            val result = mutableListOf<Transaction>()

            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                result.add(
                    Transaction(
                        amount = o.getDouble("amount").toFloat(),
                        merchant = o.getString("merchant"),
                        mode = o.getString("mode"),
                        timestamp = o.getLong("timestamp"),
                        raw = o.getString("raw"),
                        category = o.optString("category", "Uncategorized")
                    )
                )
            }

            result

        } catch (e: Exception) {
            Log.e("LOCAL_JSON", "Load error: ${e.message}")
            emptyList()
        }
    }

    fun save(context: Context, t: Transaction) {
        val existing = load(context).toMutableList()
        existing.add(t)

        val arr = JSONArray()
        existing.forEach {
            arr.put(
                JSONObject().apply {
                    put("amount", it.amount)
                    put("merchant", it.merchant)
                    put("mode", it.mode)
                    put("timestamp", it.timestamp)
                    put("raw", it.raw)
                    put("category", it.category)
                }
            )
        }

        File(context.filesDir, FILE_NAME).writeText(arr.toString())
    }

    fun update(context: Context, newList: List<Transaction>) {
        val arr = JSONArray()
        newList.forEach {
            arr.put(
                JSONObject().apply {
                    put("amount", it.amount)
                    put("merchant", it.merchant)
                    put("mode", it.mode)
                    put("timestamp", it.timestamp)
                    put("raw", it.raw)
                    put("category", it.category)
                }
            )
        }

        File(context.filesDir, FILE_NAME).writeText(arr.toString())
    }
}
