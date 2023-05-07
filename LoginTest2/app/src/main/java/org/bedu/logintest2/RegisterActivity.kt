package org.bedu.logintest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import org.bedu.logintest2.classes.Data
import org.bedu.logintest2.classes.User

const val USER_NAME = ""

class RegisterActivity : AppCompatActivity() {

    //Views declaration
    private lateinit var signText: TextView
    private lateinit var lastName: TextInputEditText
    private lateinit var givenName: TextInputEditText
    private lateinit var userName: TextInputEditText
    private lateinit var textPass: TextInputEditText
    private lateinit var registerButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Views links to use with program
        signText = findViewById(R.id.sign_in_text)
        lastName = findViewById(R.id.lastName)
        givenName = findViewById(R.id.givenName)
        userName = findViewById(R.id.emailText)
        textPass = findViewById(R.id.reg_pass)
        registerButton = findViewById(R.id.registerButton)

        var datos = Data()
        val bundle = Bundle()

        //Register button OnClickListener
        registerButton.setOnClickListener {
            val apellido = lastName.getText().toString() //Gets Last Name
            val nombre = givenName.getText().toString() //Gets Given Name
            val usuario = userName.getText().toString() //Gets the userName
            val contraseña = textPass.getText().toString() //Gets the password
            val mapa = datos.copy(loginData = mutableMapOf(contraseña to usuario))
            val user = User(usuario, 0, contraseña, "")

            if (user.register(usuario, contraseña)) {
                val arrayData = arrayOf(usuario, contraseña)

                datos.loginData.apply { ->
                    (mapa)
                }
                Toast.makeText(this, mapa.toString(), Toast.LENGTH_SHORT).show()
                bundle.putString(USER_NAME, arrayData.toString())
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            }

        }
    }
}