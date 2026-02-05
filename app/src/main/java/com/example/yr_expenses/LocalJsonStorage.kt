package com.example.yr_expenses

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object LocalJsonStorage {

    private const val FILE_NAME = "transactions.json"

    // Load existing JSON array or return empty one
    fun load(context: Context): JSONArray {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return JSONArray()

            val text = file.readText()
            JSONArray(text)
        } catch (e: Exception) {
            Log.e("LOCAL_JSON", "Load error: ${e.message}")
            JSONArray()
        }
    }

    // Append a new transaction JSON object
    fun save(context: Context, transaction: JSONObject) {
        try {
            val file = File(context.filesDir, FILE_NAME)
            val existing = load(context)

            existing.put(transaction)

            file.writeText(existing.toString())
            Log.d("LOCAL_JSON", "Saved locally: $transaction")

        } catch (e: Exception) {
            Log.e("LOCAL_JSON", "Save error: ${e.message}")
        }
    }

    // Optional: clear all data
    fun clear(context: Context) {
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (file.exists()) file.delete()
        } catch (_: Exception) {}
    }
}
