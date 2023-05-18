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
import org.bedu.logintest2.databinding.ActivityRegisterBinding

const val USER_NAME = "USER_NAME"
const val PASSWRD = "PASSWRD"

class RegisterActivity : AppCompatActivity() {

    //Views declaration
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Views links to use with program
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var datos = Data()
        val bundle = Bundle()

        //Register button OnClickListener
        binding.registerButton.setOnClickListener {
            val apellido = binding.lastName.getText().toString() //Gets Last Name
            val nombre = binding.givenName.getText().toString() //Gets Given Name
            val usuario = binding.emailText.getText().toString() //Gets the userName
            val contraseña = binding.regPass.getText().toString() //Gets the password
            val mapa = datos.copy(loginData = mutableMapOf(contraseña to usuario))
            val user = User(usuario, 0, contraseña, "")

            if (user.register(usuario, contraseña)) {

              /*  datos.loginData.apply { ->
                    (mapa)
                }*/
                Toast.makeText(this, mapa.toString(), Toast.LENGTH_SHORT).show()
                bundle.putString(USER_NAME, "${usuario}")
                bundle.putString(PASSWRD, "${contraseña}")
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            }

        }
    }
}