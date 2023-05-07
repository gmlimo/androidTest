package org.bedu.logintest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.bedu.logintest2.classes.User

class LoginActivity : AppCompatActivity() {

    //Views declaration
    private lateinit var signText: TextView
    private lateinit var userName: TextInputEditText
    private lateinit var textPass: TextInputEditText
    private lateinit var loginButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Views links
        signText = findViewById(R.id.signText)
        userName = findViewById(R.id.userNameText)
        textPass = findViewById(R.id.passText2)
        loginButton = findViewById(R.id.loginButton)

        val user = User("William", 2, "1234", "wlimonQcituspower.com")

        signText.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //On ClickListener for loginButton
        loginButton.setOnClickListener{it

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