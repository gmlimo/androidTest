package org.bedu.cafebedu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.bedu.cafebedu.databinding.ActivityRegisterBinding

val PREFS_NAME = "org.bedu.cafebedu"
val USER_KEY = "USER_KEY"
val PASS_KEY = "PASS_KEY"
lateinit var preferences: SharedPreferences

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var loginActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        loginActivity = LoginActivity()

        with (binding) {
            registerButton.setOnClickListener {
                val user = emailText.getText().toString()
                val pass = regPass.getText().toString()

            if (register(user, pass)) {
                preferences.edit()
                    .putString(USER_KEY, user)
                    .putString(PASS_KEY, pass)
                    .apply()

                changeActivity(loginActivity)

            }
                else {
                    Toast.makeText(this@RegisterActivity, getString(R.string.not_empty), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun register(user: String, password: String): Boolean {
        fun validate(input: String) = input.isNotEmpty()

        val userValited = validate(user)
        val passwordValidated = validate(password)

        return userValited && passwordValidated
    }

    private fun changeActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}