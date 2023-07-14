package org.bedu.recyclertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.bedu.recyclertest.databinding.ActivityMainBinding
import org.bedu.recyclertest.files.CartItem
import org.bedu.recyclertest.files.RecyclerAdapter

private lateinit var total: TextView


class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var items: MutableList<CartItem>
    private lateinit var mAdapater: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        total = binding.total
        items = getItems()
        setUpRecyclerView(items)

    }

    private fun setUpRecyclerView(mItems: MutableList<CartItem>){
        binding.cartView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapater = RecyclerAdapter(mItems, this@MainActivity)
            adapter = mAdapater
        }
    }



    private fun getItems() = mutableListOf(
        CartItem(R.drawable.donas, "Donuts", 2, 15.0f),
        CartItem(R.drawable.cafe, "Coffee", 1, 46.0f)
    )

    override fun onItemClick(position: Int) {
        //Toast.makeText(this, "${position}", Toast.LENGTH_SHORT).show()
        if (position == 0) {
            Snackbar.make(total, "¿Quieres remover el elemento?", Snackbar.LENGTH_LONG).setAction("Eliminar") {
                items.removeAt(position)
                mAdapater.notifyItemRemoved(position)
                setUpRecyclerView(items)
            }
                .setBackgroundTint(getColor(R.color.colorPrimary))
                .setActionTextColor(getColor(R.color.white))
                .show()
        }
        if (position == 1) {
            Snackbar.make(total, "¿Quieres remover el elemento?", Snackbar.LENGTH_LONG).setAction("Eliminar") {
                items.removeAt(position)
                mAdapater.notifyItemRemoved(position)
                setUpRecyclerView(items)
            }
                .setBackgroundTint(getColor(R.color.colorPrimary))
                .setActionTextColor(getColor(R.color.white))
                .show()
        }
        }
    }
