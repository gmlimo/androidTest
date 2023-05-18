package com.example.projectassembly

import Pastel
import Waffle
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.projectassembly.clases.Coffee
import com.example.projectassembly.clases.Data
import com.example.projectassembly.clases.Donut
import com.example.projectassembly.clases.*
import com.example.projectassembly.clases.Size

const val totalD = "totalD"
const val totalC = "totalC"
const val totalW = "totalW"
const val totalP = "totalP"
const val quantityD = "quantityD"
const val quantityC = "quantityC"

class ActivityProductSel : AppCompatActivity() {

    private lateinit var sizeSel1: Spinner
    private lateinit var sizeSel2: Spinner
    private lateinit var donutPlus: Button
    private lateinit var donutMinus: Button
    private lateinit var donutQuantity: TextView
    private lateinit var coffeePlus: Button
    private lateinit var coffeeMinus: Button
    private lateinit var coffeeQuantity: TextView
    private lateinit var irCarrito: ImageButton

    //Variables
    var size = arrayOf(" ", "Small", "Medium", "Jumbo")
    val donas = Donut("Chocolate")
    val cafe = Coffee("Capuchino")
    val waffle = Waffle("chocolate")
    val pastel = Pastel("tres leches")
    var quantity1 = 0
    var quantity2 = 0
    var priceD = 0.0f
    var priceC = 0.0f
    var priceW = 0.0f
    var priceP = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_sel)

        sizeSel1 = findViewById(R.id.donutSize)
        sizeSel2 = findViewById(R.id.coffeeSize)
        donutPlus = findViewById(R.id.donut_plus)
        donutMinus = findViewById(R.id.donut_minus)
        donutQuantity = findViewById(R.id.donutQuantity)
        coffeePlus = findViewById(R.id.coffee_plus)
        coffeeMinus = findViewById(R.id.coffee_minus)
        coffeeQuantity = findViewById(R.id.coffeeQuantity)
        irCarrito = findViewById(R.id.carrito)

        //On ClickListener for the Plus Button for Donuts
        donutPlus.setOnClickListener {
            it
            quantity1++
            donutQuantity.text = quantity1.toString()
        }

        //On ClickListener for the Minus Button for Donuts
        donutMinus.setOnClickListener {
            it
            quantity1--
            donutQuantity.text = quantity1.toString()
        }

        //On ClickListener for the Plus Button for Coffee
        coffeePlus.setOnClickListener {
            it
            quantity2++
            coffeeQuantity.text = quantity2.toString()
        }

        //On ClickListener for the Minus Button for Coffee
        coffeeMinus.setOnClickListener {
            it
            quantity2--
            coffeeQuantity.text = quantity2.toString()
        }

        //Agregamos Adapter para el click de items
        sizeSel1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Pass the selection to variable
                val size_Selection1 = size[position]
                //Get the subtotal to pay
                priceD = donas.subTotal(quantity1, size_Selection1)
                Toast.makeText(this@ActivityProductSel, "${priceD}", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showDialog(
                    "No seleccionaste el tamaño",
                    "Vuelve a desplegar la lista y asegúrate de elegir correctamente a alguna"
                )
                Toast.makeText(applicationContext, "No hay selección", Toast.LENGTH_LONG).show()
            }
        }
        //Adaptador para mostrar elementos del menú del Spinner
        val menuAdapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, size)
        sizeSel1.adapter = menuAdapter1


        sizeSel2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Pass the selection to variable
                val size_Selection2 = size[position]
                //Get the subtotal to pay
                priceC = cafe.subTotal(quantity2, size_Selection2)
                Toast.makeText(this@ActivityProductSel, "${priceC}", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showDialog(
                    "No seleccionaste el tamaño",
                    "Vuelve a desplegar la lista y asegúrate de elegir correctamente a alguna"
                )
                Toast.makeText(applicationContext, "No hay selección", Toast.LENGTH_LONG).show()
            }
        }
        //Adaptador para mostrar elementos del menú del Spinner
        val menuAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, size)
        sizeSel2.adapter = menuAdapter2

        /*When the cart button is clicked the intent of the next activity is triggered and the
        prices are sent.*/
        irCarrito.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java).also {
                it.putExtra(totalD, "$priceD")
                it.putExtra(totalC, "$priceC")
                it.putExtra(quantityD, "$quantity1")
                it.putExtra(quantityC, "$quantity2")
                //it.putExtra(totalW, "$priceW")
                //it.putExtra(totalP, "$priceP")
            }
            startActivity(intent)
        }
    }


    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface, which -> dialogInterface.dismiss() }
            .create()
            .show()
    }

/*
        //Views declarations
        val donutCheck = findViewById<CheckBox>(R.id.donutCheck)
        val coffeeCheck = findViewById<CheckBox>(R.id.coffeeCheck)
        val waffleCheck = findViewById<CheckBox>(R.id.waffleCheck)
        val pastelCheck = findViewById<CheckBox>(R.id.pastelCheck)
        val donutImage = findViewById<ImageView>(R.id.donutView)
        val plusButton = findViewById<Button>(R.id.plusButton)
        val minusButton = findViewById<Button>(R.id.minusButton)
        val irCarrito = findViewById<ImageButton>(R.id.carrito)
        val addCart = findViewById<Button>(R.id.addCart)
        val amount_TV = findViewById<TextView>(R.id.amount_TV)
        val Size = findViewById<EditText>(R.id.autoCompleteTextView)


        //If there is a change in Donas selection do this...
        donutCheck.setOnClickListener {

            quantity = 0
            amount_TV.text="0"

            if (donutCheck.isChecked) {
                //Toast.makeText(this, "Selección Donas", Toast.LENGTH_LONG).show()

                //Change image
                donutImage.setImageResource(R.drawable.donas)

                //On ClickListener for the Plus Button
                plusButton.setOnClickListener {
                    it
                    quantity++
                    amount_TV.text = quantity.toString()
                }

                //On ClickListener for the Minus Button
                minusButton.setOnClickListener {
                    it
                    quantity--
                    amount_TV.text = quantity.toString()
                }


                addCart.setOnClickListener {
                    //Get the size of the product
                    val size = Size.getText().toString()

                    //Get the subtotal to pay
                    priceD = donas.subTotal(quantity, size)
                    Toast.makeText(this, "El total es: $priceD", Toast.LENGTH_LONG).show()
                }
            }
        }
        //waffle
        waffleCheck.setOnClickListener {

            quantity = 0
            amount_TV.text="0"

            if (waffleCheck.isChecked) {
                //Toast.makeText(this, "Selección waffle", Toast.LENGTH_LONG).show()

                //Change image
                donutImage.setImageResource(R.drawable.waffle)

                //On ClickListener for the Plus Button
                plusButton.setOnClickListener {
                    it
                    quantity++
                    amount_TV.text = quantity.toString()
                }

                //On ClickListener for the Minus Button
                minusButton.setOnClickListener {
                    it
                    quantity--
                    amount_TV.text = quantity.toString()
                }


                addCart.setOnClickListener {
                    //Get the size of the product
                    val size = Size.getText().toString()

                    //Get the subtotal to pay
                    priceW = waffle.subTotal(quantity, size)
                    Toast.makeText(this, "El total es: $priceW", Toast.LENGTH_LONG).show()
                }
            }
        }

        //Pastel
        pastelCheck.setOnClickListener {

            quantity = 0
            amount_TV.text="0"

            if (pastelCheck.isChecked) {

                //Change image
                donutImage.setImageResource(R.drawable.pastel)

                //On ClickListener for the Plus Button
                plusButton.setOnClickListener {
                    it
                    quantity++
                    amount_TV.text = quantity.toString()
                }

                //On ClickListener for the Minus Button
                minusButton.setOnClickListener {
                    it
                    quantity--
                    amount_TV.text = quantity.toString()
                }


                addCart.setOnClickListener {
                    //Get the size of the product
                    val size = Size.getText().toString()

                    //Get the subtotal to pay
                    priceP = pastel.subTotal(quantity, size)
                    Toast.makeText(this, "El total es: $priceP", Toast.LENGTH_LONG).show()
                }
            } //else Toast.makeText(this, "No hay productos seleccionados", Toast.LENGTH_SHORT).show()
        }

        //If there is a change in Café selection do this...
        coffeeCheck.setOnClickListener {

            quantity = 0
            amount_TV.text="0"

            if (coffeeCheck.isChecked) {
                //Toast.makeText(this, "Selección Café", Toast.LENGTH_LONG).show()

                //Change image
                donutImage.setImageResource(R.drawable.cafe)

                //On ClickListener for the Plus Button
                plusButton.setOnClickListener {
                    it
                    quantity++
                    amount_TV.text = quantity.toString()
                }

                //On ClickListener for the Minus Button
                minusButton.setOnClickListener {
                    it
                    quantity--
                    amount_TV.text = quantity.toString()
                }


                addCart.setOnClickListener {
                    //Get the size of the product
                    val size = Size.getText().toString()

                    //Get the subtotal to pay
                    priceC = cafe.subTotal(quantity, size)
                    Toast.makeText(this, "El total es: $priceC", Toast.LENGTH_LONG).show()
                }

            }
        }

        /*When the cart button is clicked the intent of the next activity is triggered and the
        prices are sent.*/
        irCarrito.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java).also {
                it.putExtra(totalD, "$priceD")
                it.putExtra(totalC, "$priceC")
                it.putExtra(totalW, "$priceW")
                it.putExtra(totalP, "$priceP")
            }
            startActivity(intent)
        }*/
    }
