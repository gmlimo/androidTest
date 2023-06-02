package org.bedu.menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import org.bedu.menus.databinding.ActivityContextMenuBinding


class ContextMenuActivity : AppCompatActivity() {

    //Variables globales
    private lateinit var binding: ActivityContextMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_context_menu)

        //Data Binding
        binding = ActivityContextMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Registra o detecta el evento de press long
        registerForContextMenu(binding.txTextView)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.copy -> {
                Toast.makeText(this, "Copy", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }
}
