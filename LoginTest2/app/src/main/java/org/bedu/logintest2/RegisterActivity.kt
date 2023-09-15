package org.bedu.logintest2

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.bedu.logintest2.classes.Data
import org.bedu.logintest2.classes.User
import org.bedu.logintest2.databinding.ActivityRegisterBinding

const val USER_NAME = "USER_NAME"
const val PASSWRD = "PASSWRD"

private lateinit var auth: FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    //Views declaration
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Views links to use with program
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

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

            createAccount(usuario, contraseña)


            bundle.putString(USER_NAME, "${usuario}")
            bundle.putString(PASSWRD, "${contraseña}")
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)

        }
    }
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Log.d(TAG, "createAccount: success")
                    val user = auth.currentUser
                    //updateUI(user, null)
                } else {
                    Log.w(TAG, "createAccount: failure", task.exception)
                    //updateUI(null, task.exception)
                }
            }
    }
}