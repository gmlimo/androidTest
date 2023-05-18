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
import org.bedu.logintest2.classes.datos
import org.bedu.logintest2.classes.match

var mapa: Map<String, String> = mutableMapOf(
    "1234" to "gmlimon",
    "hola" to "teaby",
    "5678" to "mbravo"
)
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

            val bundle = intent.extras
            val R_user = bundle?.getString(USER_NAME)
            val R_passwrd = bundle?.getString(PASSWRD)

            if (R_user != null) {
                if (R_passwrd != null) {
                    mapa = mutableMapOf(R_passwrd to R_user)
                }
            }

            //Toast.makeText(this, "$R_user " + "$R_passwrd", Toast.LENGTH_SHORT).show()

            if (login(usuario, contraseña)){
                //println("Login exitoso")
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()


            } else {
                //println("Usuario y/o contraseña incorrectos")
                Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

fun login(user: String, password: String): Boolean {

    fun validate(input: String) = input.isNotEmpty()

    val userValidated = validate(user)
    val passwordValidated = validate(password)

    for ((key, value) in mapa){
        //println("The password is $key and the user is $value")
        if (key == password && value == user) match = true else match = false
        if (match == true) break
    }

    return userValidated && passwordValidated && match
}