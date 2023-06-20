package org.bedu.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceiverOne : BroadcastReceiver() {

    companion object {
        val KEY_NAME = "NAME"
        val KEY_EMAIL = "EMAIL"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        val name = bundle?.getString("NAME")
        val email = bundle?.getString("EMAIL")
        Toast.makeText(context,"$name $email",Toast.LENGTH_LONG).show()
    }
}