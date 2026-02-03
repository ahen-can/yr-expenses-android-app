package com.example.yr_expenses

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsConsentReceiver(private val activity: Activity) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != SmsRetriever.SMS_RETRIEVED_ACTION) return

        val extras: Bundle = intent.extras ?: return

        // ---- Get Status (API-safe) ----
        val status: Status? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                extras.getParcelable(SmsRetriever.EXTRA_STATUS, Status::class.java)
            } else {
                @Suppress("DEPRECATION")
                extras.getParcelable(SmsRetriever.EXTRA_STATUS)
            }

        when (status?.statusCode) {

            CommonStatusCodes.SUCCESS -> {
                // ---- Get Consent Intent (API-safe) ----
                val consentIntent: Intent? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        extras.getParcelable(
                            SmsRetriever.EXTRA_CONSENT_INTENT,
                            Intent::class.java
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT)
                    }

                try {
                    activity.startActivityForResult(consentIntent, 200)
                } catch (e: Exception) {
                    Log.e("SMS_CONSENT", "Could not launch consent activity: ${e.message}")
                }
            }

            CommonStatusCodes.TIMEOUT -> {
                Log.e("SMS_CONSENT", "❌ Timeout waiting for SMS")
            }
        }
    }
}
