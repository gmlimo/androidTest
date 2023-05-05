package com.example.figmatest2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.figmatest1.clases.User


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Views declaration to use with program
        val signText = findViewById<TextView>(R.id.signText)
        val userName = findViewById<EditText>(R.id.user_textView)
        val textPass = findViewById<EditText>(R.id.pass_textView)
        val loginButton = findViewById<ImageButton>(R.id.login_button)



        val user = User("William", 2, "1234", "wlimonQcituspower.com")

        signText.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(USER_NAME, "Hola")
            val intent = Intent(this, ActivitySecond::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }

        //On ClickListener for loginButton
        loginButton.setOnClickListener{it

            val bundle = intent.extras
            val receivedData = bundle?.getString(USER_NAME).toString()
            Toast.makeText(this, "${receivedData}", Toast.LENGTH_LONG).show()
            Log.d(this.toString(), "${receivedData}") /*Aquí no se muestra la información
             de la actividad anterior. La intención es obtener usuario y constaseña del registro*/

            val usuario = userName.getText().toString() //Gets the userName
            val contraseña = textPass.getText().toString() //Gets the password

            if (user.login(usuario, contraseña)){
                //println("Login exitoso")
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()

                //Next activity call on a successfull login
              /*  val intent = Intent(this, ActivityProductSel::class.java).also{
                    it.putExtra("extraG", "Bienvenido")
                }
                startActivity(intent)*/

            } else {
                //println("Usuario y/o contraseña incorrectos")
                Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

    }
}