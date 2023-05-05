package com.example.figmatest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.figmatest1.clases.Data
import com.example.figmatest1.clases.User

const val USER_NAME = ""

class ActivitySecond : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //Views declaration to use with program
        val signText = findViewById<TextView>(R.id.signText2)
        val userName = findViewById<EditText>(R.id.user_textView2)
        val textPass = findViewById<EditText>(R.id.pass_textView2)
        val registerButton = findViewById<ImageButton>(R.id.register_button)

        var datos = Data()
        val bundle = Bundle()

        //Register button OnClickListener
        registerButton.setOnClickListener {
            val usuario = userName.getText().toString() //Gets the userName
            val contraseña = textPass.getText().toString() //Gets the password
            val mapa = datos.copy(loginData = mutableMapOf(contraseña to usuario))
            val user = User(usuario, 0, contraseña, "")

            if (user.register(usuario, contraseña)) {
                val arrayData = arrayOf(usuario, contraseña)

                datos.loginData.apply { ->
                    (mapa)
                }
                //Toast.makeText(this, mapa.toString(), Toast.LENGTH_SHORT).show()
                bundle.putString(USER_NAME, arrayData.toString())
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            }

        }
    }
}

