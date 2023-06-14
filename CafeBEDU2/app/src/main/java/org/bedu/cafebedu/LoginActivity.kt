package org.bedu.cafebedu

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.Toast
import org.bedu.cafebedu.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var registerActivity: Activity
    private lateinit var productSelAct: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerActivity = RegisterActivity()
        productSelAct = ProductSelActivity()

        with (binding) {

            signText.setOnClickListener {
                configTransition()
                changeActivity(registerActivity)
            }

            loginButton.setOnClickListener {
                if (getLoginData()) {
                    configTransition()
                    changeActivity(productSelAct)
                    Toast.makeText(this@LoginActivity, getString(R.string.success), Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@LoginActivity, getString(R.string.fail), Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun changeActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    private fun getLoginData(): Boolean {
        val user = binding.userNameText.getText().toString()
        val password = binding.passText2.getText().toString()

        fun validate(input: String) = input.isNotEmpty()

        val userValidated = validate(user)
        val passwordValidated = validate(password)

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val r_user = preferences.getString(USER_KEY, "")
        val r_pass = preferences.getString(PASS_KEY, "")
        var match = false

        if (user == r_user && password == r_pass) match = true else match = false
        return userValidated && passwordValidated && match
    }

    private fun configTransition() {
        val transitionXml = TransitionInflater
            .from(this).inflateTransition(R.transition.activity_transition).apply {
                excludeTarget(android.R.id.statusBarBackground, true)
                excludeTarget(android.R.id.navigationBarBackground, true)
            }

        window.exitTransition = transitionXml
    }
}