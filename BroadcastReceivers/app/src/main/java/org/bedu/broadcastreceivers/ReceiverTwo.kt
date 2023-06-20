package org.bedu.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceiverTwo: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            val bundle = intent.extras
            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")
            withContext(Dispatchers.IO){
                delay(3000)
                Log.d("Thread", "Second msg")
            }
        }
        Toast.makeText(context,"Evento recibido 2",Toast.LENGTH_SHORT).show()

    }

}