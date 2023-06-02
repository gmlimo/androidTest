package org.bedu.menus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import org.bedu.menus.databinding.ActivityMainBinding
import org.bedu.menus.databinding.ActivityOptionsMenuBinding

class MainActivity : AppCompatActivity() {

    //Variables Globales
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Options Menu
        binding.optionsMenuButton.setOnClickListener{
            startActivity(Intent(this, OptionsMenuActivity::class.java))
        }

        //Context Menu
        binding.contextualMenuButton.setOnClickListener {
            startActivity((Intent(this, ContextMenuActivity::class.java)))
        }

        //Popup Menu
        binding.popupMenuButton.setOnClickListener {
            startActivity((Intent(this, PopUpMenuActivity::class.java)))
        }
    }
}