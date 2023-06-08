package org.bedu.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "org.bedu.sharedpreferences"

    private val BOOLEAN_KEY = "boolean_key"
    private val STRING_KEY = "string_key"
    private val NUMBER_KEY = "number_key"


    private lateinit var preferences: SharedPreferences

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        //Save info
        with (binding) {
            button.setOnClickListener {
                val boolean = switch1.isChecked
                val number = etNumber.text.toString().toFloat()
                val string = etString.text.toString()

                preferences.edit()
                    .putBoolean(BOOLEAN_KEY, boolean)
                    .putFloat(NUMBER_KEY, number)
                    .putString(STRING_KEY, string)
                    .apply()
            }
        }
        //Retrieve info
        setValues()
    }
    fun setValues(){
        val boolean = preferences.getBoolean(BOOLEAN_KEY, false)
        val number = preferences.getFloat(NUMBER_KEY,0f)
        val string = preferences.getString(STRING_KEY,"")

        with (binding) {
            //los atamos a sus vistas
            etString.setText(string)
            switch1.isChecked = boolean
            etNumber.setText(number.toString())
        }
    }

}