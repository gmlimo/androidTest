package org.bedu.cafebedu

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.bedu.cafebedu.files.Coffee
import org.bedu.cafebedu.files.Donut
import org.bedu.cafebedu.databinding.FragmentCardBinding

val PRICE_D = "price_d"
val QUANTITY1 = "quantity_1"
val PRICE_C = "price_c"
val QUANTITY2 = "quantity_2"


class CardFragment : Fragment(R.layout.fragment_card) {

    private var fragmentBlankBinding: FragmentCardBinding? = null

    //val pref = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    //Product classes initialization
    val donas = Donut("Chocolate")
    val coffee = Coffee("Capuchino")
    val args = Bundle()

    //Variables to increment or decrement the quantity of our products
    var quantity1 = 0
    var size_Selection1 = " "
    var quantity2 = 0
    var size_Selection2 = " "

    //Variables to calculate the subtotal of the purchase
    var priceD = 0.0f
    var priceC = 0.0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     super.onViewCreated(view, savedInstanceState)

        //Options for the Spinners
        var size = arrayOf(" ", getString(R.string.small), getString(R.string.medium), getString(R.string.jumbo))

        val binding = FragmentCardBinding.bind(view)
        fragmentBlankBinding = binding

        with (binding) {
            donutPlus.setOnClickListener {
                quantity1++
                if (quantity1 > 50) {
                    quantity1 = 50
                    Toast.makeText(
                        this@CardFragment?.requireContext(),
                        getString(R.string.upperLimit),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                donutQuantity.text = quantity1.toString()
            }

            donutMinus.setOnClickListener {
                quantity1--
                if (quantity1 < 0) {
                    quantity1 = 0
                    Toast.makeText(
                        this@CardFragment?.requireContext(),
                        getString(R.string.lowerLimit),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                donutQuantity.text = quantity1.toString()
            }

            //On ClickListener for the Plus Button for Coffee
            coffeePlus.setOnClickListener {
                it
                quantity2++
                //Set quantity upper limit
                if (quantity2 > 50) {
                    quantity2 = 50
                    Toast.makeText(
                        this@CardFragment?.requireContext(),
                        getString(R.string.upperLimit),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Update the donuts quantity
                coffeeQuantity.text = quantity2.toString()
            }

            //On ClickListener for the Minus Button for Coffee
            coffeeMinus.setOnClickListener {
                it
                quantity2--
                //Set quantity lower limit
                if (quantity2 < 0) {
                    quantity2 = 0
                    Toast.makeText(
                        this@CardFragment?.requireContext(),
                        getString(R.string.lowerLimit),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Update the donuts quantity
                coffeeQuantity.text = quantity2.toString()
            }

            donutSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Pass the selection to variable
                    size_Selection1 = size[position]
                    //Get the subtotal to pay

                    if (size_Selection1 != " ") {
                        priceD = donas.subTotal(quantity1, size_Selection1)


                        //val shared: SharedPreferences? =
                            //activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                        args.putInt("QUANTITY1", quantity1)
                        args.putFloat("PRICE_D", priceD)
                        cartFragment.arguments = args

                    }

               /*     pref?.edit()
                        ?.putInt(QUANTITY1, quantity1)
                        ?.putFloat(PRICE_D, priceD)
                        ?.apply()*/
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    showDialog(
                        getString(R.string.size_not_selected),
                        getString(R.string.Select_again)
                    )
                    Toast.makeText(this@CardFragment?.requireContext(), getString(R.string.no_selection), Toast.LENGTH_LONG).show()
                }
            }
            //Adaptador para mostrar elementos del menú del Spinner
            val menuAdapter1 = ArrayAdapter(this@CardFragment?.requireContext() as Context, android.R.layout.simple_spinner_item, size)
            donutSize.adapter = menuAdapter1

            coffeeSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Pass the selection to variable
                    size_Selection2 = size[position]
                    //Get the subtotal to pay

                    if (size_Selection2 != " ") {
                        priceC = coffee.subTotal(quantity2, size_Selection2)


                        args.putInt("QUANTITY2", quantity2)
                        args.putFloat("PRICE_C", priceC)
                        cartFragment.arguments = args

                    }

                    /*     pref?.edit()
                             ?.putInt(QUANTITY1, quantity1)
                             ?.putFloat(PRICE_D, priceD)
                             ?.apply()*/
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    showDialog(
                        getString(R.string.size_not_selected),
                        getString(R.string.Select_again)
                    )
                    Toast.makeText(this@CardFragment?.requireContext(), getString(R.string.no_selection), Toast.LENGTH_LONG).show()
                }
            }
            //Adaptador para mostrar elementos del menú del Spinner
            val menuAdapter2 = ArrayAdapter(this@CardFragment?.requireContext() as Context, android.R.layout.simple_spinner_item, size)
            coffeeSize.adapter = menuAdapter2


        }

    }
    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this@CardFragment?.requireContext() as Context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface, which -> dialogInterface.dismiss() }
            .create()
            .show()
    }


}