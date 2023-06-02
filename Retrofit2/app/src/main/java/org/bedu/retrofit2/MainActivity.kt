package org.bedu.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.retrofit2.api.Api
import org.bedu.retrofit2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.buttonAccept.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Valores vacíos",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = Api.loginService.login(email, password)

                    withContext(Dispatchers.Main) {
                        if (result.isSuccessful) {
                            onSuccess(result.body())
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Tu correo o contraseña es incorrecto",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (cause: Throwable) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "Error de la API",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}