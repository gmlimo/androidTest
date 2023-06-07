package org.bedu.fragments5

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.bedu.fragments5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Variables Globales
    private lateinit var binding: ActivityMainBinding

    val fragment1 = Fragment1()
    val fragment2 = Fragment2()
    val fragment3 = Fragment3()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Logica de programación
        setCurrentFragment(fragment1) //Aquí carga desde el inicio el Fragment 1
        createFragments()
    }

    private fun createFragments(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home->{
                    setCurrentFragment(fragment1)
                    it.actionView?.clearFocus()
                    true
                }
                R.id.nav_driver->{
                    setCurrentFragment(fragment2)
                    it.actionView?.clearFocus()
                    true
                }

                R.id.nav_history->{

                    /*Para mandar datos a otros fragments o actividades a través de una base de datos
                    dinámica de Shared Preferences
                     */
                    val args = Bundle()
                    val shared: SharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
                    args.putInt("idConductor", shared.getInt("idConductor",1234))
                    args.putString("token", shared.getString("token","x7z1"))
                    fragment3.arguments=args
                    setCurrentFragment(fragment3)
                    true
    /*                setCurrentFragment(fragment3)
                    it.actionView?.clearFocus()
                    true*/
                }
                else -> false
            }
        }
//        bottomNavigationView.getOrCreateBadge(R.id.nav_home).apply {
//            isVisible=true
//            number=8
//        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView,fragment)
            commit()
        }
    }
}