package com.example.yr_expenses

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val fullBody = messages.joinToString("") { it.displayMessageBody }
        val sender = messages.first().displayOriginatingAddress

        Log.d("BANK_SMS", "From=$sender | Body=$fullBody")

        // Parse SMS immediately (no Room)
        SmsParser.handleMessage(context, sender, fullBody)
    }
}
