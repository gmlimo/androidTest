package edu.upbc.sra.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import java.sql.Connection

class WifiReceiver(): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = checkConnection(context)
        if (connectionReceiverListener != null) connectionReceiverListener!!.onNetworkConnectionChanged(isConnected)

        Toast.makeText(
            context,
            "Wifi enabled: $isConnected",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    interface ConnectionReceiverListener{
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectionReceiverListener: ConnectionReceiverListener? = null
        //val isConnected: Boolean
            //get() {}
    }

    private fun checkConnection(context: Context): Boolean {
        val cn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cn.activeNetworkInfo
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting)
    }

}
