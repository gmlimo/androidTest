package org.bedu.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.broadcastreceivers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val receiverTwo: BroadcastReceiver = ReceiverTwo()

    private val airplaneReceiver = AirplaneReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val bundle = Bundle().apply {
                putString(ReceiverOne.KEY_NAME, "Martin")
                putString(ReceiverOne.KEY_EMAIL, "gmlimon@gmail.com")
            }


            IntentFilter().apply {
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            }.also { filter -> registerReceiver(airplaneReceiver,filter) }


            Intent("org.bedu.actions.SALUDO").apply {
                putExtras(bundle)
            }.let(::sendBroadcast)


            /*Intent(this, ReceiverOne::class.java).apply {
                putExtras(bundle)
            }.let(::sendBroadcast)*/
        }

    }

    override fun onStart() {
        super.onStart()

        IntentFilter().apply {
            addAction("org.bedu.actions.SALUDO")
        }.also { filter -> registerReceiver(receiverTwo, filter) }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiverTwo)
    }

  /*  override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneReceiver)
    }*/
}