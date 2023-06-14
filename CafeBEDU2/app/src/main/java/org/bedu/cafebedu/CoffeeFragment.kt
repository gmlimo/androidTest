package org.bedu.cafebedu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.bedu.cafebedu.databinding.FragmentCardBinding
import org.bedu.cafebedu.databinding.FragmentCoffeeBinding
import org.bedu.cafebedu.files.Coffee
import org.bedu.cafebedu.files.Donut



class CoffeeFragment : Fragment(R.layout.fragment_coffee) {

    private var fragmentBlankBinding: FragmentCoffeeBinding? = null

    //val pref = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    //Product classes initialization
    val coffee = Coffee("Capuchino")

    //Variables to increment or decrement the quantity of our products
    var quantity2 = 0
    var size_Selection2 = " "

    //Variables to calculate the subtotal of the purchase
    var priceC = 0.0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCoffeeBinding.bind(view)
        fragmentBlankBinding = binding

        //Options for the Spinners
        var size = arrayOf(" ", getString(R.string.small), getString(R.string.medium), getString(R.string.jumbo))

        with (binding) {
            coffeePlus.setOnClickListener {
                quantity2++
                if (quantity2 > 50) {
                    quantity2 = 50
                    Toast.makeText(
                        this@CoffeeFragment?.requireContext(),
                        getString(R.string.upperLimit),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                coffeeQuantity.text = quantity2.toString()
            }

            coffeeMinus.setOnClickListener {
                quantity2--
                if (quantity2 < 0) {
                    quantity2 = 0
                    Toast.makeText(
                        this@CoffeeFragment?.requireContext(),
                        getString(R.string.lowerLimit),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                coffeeQuantity.text = quantity2.toString()
            }

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

                        val args = Bundle()
                        //val shared: SharedPreferences? =
                        //activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
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
                    Toast.makeText(this@CoffeeFragment?.requireContext(), getString(R.string.no_selection), Toast.LENGTH_LONG).show()
                }
            }
            //Adaptador para mostrar elementos del menú del Spinner
            val menuAdapter2 = ArrayAdapter(this@CoffeeFragment?.requireContext() as Context, android.R.layout.simple_spinner_item, size)
            coffeeSize.adapter = menuAdapter2

        }

    }
    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this@CoffeeFragment?.requireContext() as Context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface, which -> dialogInterface.dismiss() }
            .create()
            .show()
    }
        }




