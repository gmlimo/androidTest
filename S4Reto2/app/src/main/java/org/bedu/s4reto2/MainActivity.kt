package org.bedu.s4reto2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //Variables globales
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Conexiones con las clases ya creadas
        recycler = findViewById(R.id.recycler)
        recycler.adapter = RecyclerAdapter(listOf(
            Game(R.drawable.ic_launcher_foreground, "Devil May Cry 5", "Action", 4, "Teen"),
            Game(R.drawable.ic_launcher_background, "Destiny 2", "Shooter", 3, "Teen")
        ))
    }
}