package org.bedu.simplenotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.simplenotification.databinding.ActivityMainBinding
import org.bedu.simplenotification.utils.executeOrRequestPermission
import org.bedu.simplenotification.utils.simpleNotification

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnNotify.setOnClickListener {
                executeOrRequestPermission(this@MainActivity) {
                    simpleNotification(this@MainActivity)
                }
            }
        }
    }
}