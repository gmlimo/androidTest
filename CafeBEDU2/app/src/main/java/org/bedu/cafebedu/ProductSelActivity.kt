package org.bedu.cafebedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import org.bedu.cafebedu.databinding.ActivityProductSelBinding

val donutCard = CardFragment()
val coffeeFragment = CoffeeFragment()
val cartFragment = CartFragment()

class ProductSelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductSelBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductSelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment1(donutCard)
        createFragments()

        binding.carrito.setOnClickListener {
            setCurrentFragment1(cartFragment)

        }

    }

    private fun setCurrentFragment1(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            addToBackStack(null)
            commit()
        }
    }


    private fun createFragments() {
        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    setCurrentFragment1(donutCard)
                    it.actionView?.clearFocus()
                    true
                }
                R.id.nav_history -> {
                    setCurrentFragment1(cartFragment)
                    it.actionView?.clearFocus()
                    true
                }
            }
        }
    }
}