package com.example.yr_expenses

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject

object ApiClient {

    private const val SERVER = "http://100.83.194.98:5000/api/transactions"

    fun send(context: Context, json: JSONObject) {
        Fuel.post(SERVER)
            .header("Content-Type" to "application/json")
            .body(json.toString())
            .response { _, _, result ->
                result.fold(
                    success = {
                        Log.d("API", "Sent OK! -> $json")
                    },
                    failure = {
                        Log.e("API", "FAILED: ${it.message}")
                    }
                )
            }
    }
}
