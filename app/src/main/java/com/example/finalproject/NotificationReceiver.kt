package com.example.finalproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        when (action) {
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(context,"Battery low",Toast.LENGTH_LONG).show()
            }
            Intent.ACTION_BATTERY_OKAY -> {
                Toast.makeText(context,"Battery ok", Toast.LENGTH_LONG).show()
            }
        }
    }
}