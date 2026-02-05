package com.example.yr_expenses

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object LocalStorage {

    private const val FILE_NAME = "transactions.json"

    fun loadAll(context: Context): List<JSONObject> {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return emptyList()

            val json = file.readText()
            val arr = JSONArray(json)

            val list = mutableListOf<JSONObject>()
            for (i in 0 until arr.length()) {
                list.add(arr.getJSONObject(i))
            }
            list
        } catch (e: Exception) {
            Log.e("LOCAL_JSON", "Read error: ${e.message}")
            emptyList()
        }
    }

    fun save(context: Context, obj: JSONObject) {
        try {
            val file = File(context.filesDir, FILE_NAME)
            val arr = if (file.exists()) {
                JSONArray(file.readText())
            } else JSONArray()

            arr.put(obj)

            file.writeText(arr.toString())
            Log.d("LOCAL_JSON", "Saved locally: $obj")
        } catch (e: Exception) {
            Log.e("LOCAL_JSON", "Write error: ${e.message}")
        }
    }
}
