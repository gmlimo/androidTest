package org.bedu.logintest2

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
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

    private lateinit var preferences: SharedPreferences

    private val PREFS_NAME = "org.bedu.sharedpreferences"
    private val USER_KEY = "user_key"
    private val PASSWORD_KEY = "password_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Views links
        signText = findViewById(R.id.signText)
        userName = findViewById(R.id.userNameText)
        textPass = findViewById(R.id.passText2)
        loginButton = findViewById(R.id.loginButton)

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) //Modo privado

        val transitionXml = TransitionInflater
            .from(this).inflateTransition(R.transition.activity_transition).apply {
                excludeTarget(android.R.id.statusBarBackground, true)
                excludeTarget(android.R.id.navigationBarBackground, true)
            }

        window.exitTransition = transitionXml


        signText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //On ClickListener for loginButton
        loginButton.setOnClickListener {
            val usuario = userName.getText().toString() //Gets the userName
            val contraseña = textPass.getText().toString() //Gets the password

            preferences.edit()
                .putString(USER_KEY, usuario)
                .putString(PASSWORD_KEY, contraseña)
                .apply()


        }
    }


    fun login(user: String, password: String): Boolean {

        fun validate(input: String) = input.isNotEmpty()

        val userValidated = validate(user)
        val passwordValidated = validate(password)

        for ((key, value) in mapa) {
            //println("The password is $key and the user is $value")
            if (key == password && value == user) match = true else match = false
            if (match == true) break
        }

        return userValidated && passwordValidated && match
    }
}