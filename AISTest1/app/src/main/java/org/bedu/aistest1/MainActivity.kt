package org.bedu.aistest1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import org.bedu.aistest1.classes.ItemAdapter
import org.bedu.aistest1.classes.Option

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declares the array to place the objects (options for the menu)
        val items: ArrayList<Option> = ArrayList<Option>()

        //Add the options for the menu
        items.add(0, Option(R.drawable.ais1, getString(R.string.automatic_irrigation_system)))
        items.add(1, Option(R.drawable.ais2, getString(R.string.catalog)))
        items.add(2, Option(R.drawable.ais3, getString(R.string.browse)))

        //Use the itemAdapter to display the ArrayList
        val itemAdapter: ItemAdapter = ItemAdapter(this, items)
        val listView: ListView = findViewById(R.id.optionsList)
        listView.setAdapter(itemAdapter)

        listView.isClickable = true
        listView.setOnItemClickListener( AdapterView.OnItemClickListener(){
            parent, view, position, id ->

            val intent = Intent(this, AIS_Main::class.java)

            when(position){
                0 -> startActivity(intent)
                1 -> Toast.makeText(applicationContext, "Seleccionaste el catálogo", Toast.LENGTH_LONG).show()
                2 -> Toast.makeText(applicationContext, "Seleccionaste el buscador", Toast.LENGTH_LONG).show()
            }
            //Toast.makeText(applicationContext, "Seleccionaste ${items[position]}", Toast.LENGTH_LONG).show()
        })
    }
}