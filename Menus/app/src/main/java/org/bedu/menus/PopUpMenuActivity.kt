package org.bedu.menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.PopupMenu
import org.bedu.menus.databinding.ActivityPopUpMenuBinding

class PopUpMenuActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPopUpMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_pop_up_menu)

        binding = ActivityPopUpMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerForContextMenu(binding.button)
        binding.button.setOnClickListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.popup_menu, menu)
    }

    override fun onClick(v: View?) {
        var popMenu = PopupMenu(this, v)
        popMenu.menuInflater.inflate(R.menu.popup_menu, popMenu.menu)
        popMenu.show()
    }

}