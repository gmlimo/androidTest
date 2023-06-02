package org.bedu.menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import org.bedu.menus.databinding.ActivityOptionsMenuBinding

class OptionsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_menu)

        val binding = ActivityOptionsMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
