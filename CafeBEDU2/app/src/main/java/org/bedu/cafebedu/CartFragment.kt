package org.bedu.cafebedu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.bedu.cafebedu.databinding.FragmentCartBinding
import org.bedu.cafebedu.files.CartItem
import org.bedu.cafebedu.files.Data
import org.bedu.cafebedu.files.RecyclerAdapter

private lateinit var total: TextView


class CartFragment : Fragment(R.layout.fragment_cart), RecyclerAdapter.OnItemClickListener {

    private var fragmentBlankBinding: FragmentCartBinding? = null
    private lateinit var recycler: RecyclerView

    var price1 = 0.0f
    var price2 = 0.0f
    var quantity1 = 0
    var quantity2 = 0
    var message = ""
    val datos = Data()

    //val pref = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCartBinding.bind(view)
        fragmentBlankBinding = binding

        recycler = binding.cartView
        total = binding.total

        price1 = arguments?.getFloat("PRICE_D") ?: 0.0f
        quantity1 = arguments?.getInt("QUANTITY1") ?: 0
        price2 = arguments?.getFloat("PRICE_C") ?: 0.0f
        quantity2 = arguments?.getInt("QUANTITY2") ?: 0

        var aux = 0


        if (price1 != 0.0f) {
            if (price2 == 0.0f) aux = 1
        }
        if (price2 != 0.0f) {
            if (price1 == 0.0f) aux = 2
        }


        when (aux) {
            1 -> recycler.adapter = RecyclerAdapter(listOf(
                CartItem(R.drawable.donas, "Donuts", quantity1, price1 )
            ), this)
            2 -> recycler.adapter = RecyclerAdapter(listOf(
                CartItem(R.drawable.cafe, "Coffee", quantity2, price2 )), this)
            else -> recycler.adapter = RecyclerAdapter(listOf(
                CartItem(R.drawable.donas, "Donuts",quantity1, price1 ),
                CartItem(R.drawable.cafe, "Coffee", quantity2, price2 )
            ), this)
        }

        message = getString(R.string.Amount_to_pay) + "${(price1 + price2) * datos.iva}"
        binding.total.text = message

    }

    override fun onItemClick(position: Int) {

        //Toast.makeText(this, "${position}", Toast.LENGTH_SHORT).show()
        if (position == 0) {
            Snackbar.make(total, getString(R.string.remove_item), Snackbar.LENGTH_LONG).setAction("Action") {
                recycler.adapter = RecyclerAdapter(listOf(
                    CartItem(R.drawable.cafe, "Coffee", quantity2, price2 )), this)
                message = getString(R.string.Amount_to_pay) + "${(price2) * datos.iva}"
                total.text = message
            }
                .setBackgroundTint(getColor(requireContext(), R.color.teal_200))
                .setActionTextColor(getColor(requireContext(), R.color.white))
                .show()
            recycler.adapter?.notifyItemChanged(position)
        }
        if (position == 1) {
            Snackbar.make(total, getString(R.string.remove_item), Snackbar.LENGTH_LONG).setAction(getString(
                            R.string.delete)) {
                recycler.adapter = RecyclerAdapter(listOf(
                    CartItem(R.drawable.donas, "Donuts", quantity1, price1 )), this)
                message = getString(R.string.Amount_to_pay) + "${(price1) * datos.iva}"
                total.text = message
            }
                .setBackgroundTint(getColor(requireContext(), R.color.teal_200))
                .setActionTextColor(getColor(requireContext(), R.color.white))
                .show()
            recycler.adapter?.notifyItemChanged(position)
        }
    }
}


