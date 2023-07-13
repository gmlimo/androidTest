package org.bedu.broadcastreceivers


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bedu.broadcastreceivers.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = Bundle().apply {
            putString("KEY_NAME", "Martin")
            putString("KEY_EMAIL", "gmlimon@gmail.com")
        }
        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000)
                Intent("org.bedu.receiver.DATA_TRANSFER").apply {
                    putExtras(bundle)
                }.let(::sendBroadcast)
            }
            }

        }
}