package org.bedu.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import org.bedu.notifications.utils.ACTION_RECEIVED

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_RECEIVED) {
            Toast.makeText(context, "Emision recibida", Toast.LENGTH_LONG).show()
        }
    }
}