package com.synfusion.shiya.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class ServiceRestarter : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.synfusion.shiya.RESTART_SERVICE") {
            val serviceIntent = Intent(context, ShiyaForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        }
    }
}
