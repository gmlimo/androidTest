package com.example.projectassembly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectassembly.clases.CartItem
import com.example.projectassembly.clases.Data
import com.example.projectassembly.clases.RecyclerAdapter


class CartActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var total: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recycler = findViewById(R.id.cartView)
        total = findViewById(R.id.total)

        //Reads the putExtra intent from previous Activity
        val price1 = (intent.getStringExtra("totalD")?.toFloat()) ?: 0.0f

        val price2 = (intent.getStringExtra("totalC")?.toFloat()) ?: 0.0f

        val price3 = (intent.getStringExtra("totalW")?.toFloat()) ?: 0.0f

        val price4 = (intent.getStringExtra("totalP")?.toFloat()) ?: 0.0f

        val quantity1 = (intent.getStringExtra("quantityD")?.toInt()) ?: 0

        val quantity2 = (intent.getStringExtra("quantityC")?.toInt()) ?: 0

        var aux = 0


        if (price1 != 0.0f) {
            if (price2 == 0.0f) aux = 1
        }
        if (price2 != 0.0f) {
            if (price1 == 0.0f) aux = 2
        }


        when (aux) {
            1 -> recycler.adapter = RecyclerAdapter(listOf(
                CartItem(R.drawable.donas, "Donuts", quantity1, price1 )))
            2 -> recycler.adapter = RecyclerAdapter(listOf(
                CartItem(R.drawable.cafe, "Coffee", quantity2, price2 )))
            else -> recycler.adapter = RecyclerAdapter(listOf(
                CartItem(R.drawable.donas, "Donuts",quantity1, price1 ),
                CartItem(R.drawable.cafe, "Coffee", quantity2, price2 )
            ))
        }
        val datos = Data()
        total.text ="El total a pagar es $ ${(price1 + price2) * datos.iva}"


        /*
        //Display the amount to pay
        val datos = Data()
        Toast.makeText(
            this,
            "El total es: ${(price1 + price2 + price3 + price4) * datos.iva}",
            Toast.LENGTH_LONG
        ).show()

        //Declares de array to place the objects (items to purchase)
        val items: ArrayList<CartItem> = ArrayList<CartItem>()


        if (price1 != 0.0f) {
            items.add(CartItem(R.drawable.donas, "Donas", price1))
        }
        if (price2 != 0.0f) {
            items.add(CartItem(R.drawable.cafe, "Café", price2))
        }
        if (price3 != 0.0f) {
            items.add(CartItem(R.drawable.waffle, "Waffle", price3))
        }
        if (price4 != 0.0f) {
            items.add(CartItem(R.drawable.pastel, "Pastel", price4))
        }

        //Use the itemAdapter to display the ArrayList
        val itemAdapter: ItemAdapter = ItemAdapter(this, items)
        val listView: ListView = findViewById(R.id.cartView)
        listView.setAdapter(itemAdapter)*/


    }
}