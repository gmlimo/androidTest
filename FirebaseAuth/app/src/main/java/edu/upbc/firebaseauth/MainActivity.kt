package edu.upbc.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.upbc.firebaseauth.databinding.ActivityMainBinding
import edu.upbc.firebaseauth.utils.setFragment
import edu.upbc.firebaseauth.utils.showMessage

val loginFragment = LoginFragment()
val registerFragment = RegisterFragment()

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        val bundle = intent.extras
        var user: String? = ""
        var password: String? = ""
        val is_reg = bundle?.getBoolean(ISREG_KEY)

        val is_Register: Boolean = is_reg ?: false
        if (is_Register) {
            user = bundle?.getString(REG_USER_KEY).toString()
            password = bundle?.getString(REG_PASSWORD_KEY).toString()
            createAccount(user, password)
        }
        else {
            user = bundle?.getString(USER_KEY).toString()
            password = bundle?.getString(PASSWORD_KEY).toString()
            verifyAccount(user, password)
        }

        binding.signText.setOnClickListener {
            binding.signText.isVisible = false
            setFragment(R.id.containerView, registerFragment)
        }

        binding.signText.isVisible = true
        //showMessage(this@MainActivity, "User is: ${user} and the password is: ${password}  ${is_reg}")
        setFragment(R.id.containerView, loginFragment)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Log.d(TAG, "createAccount: success")
                    val user = auth.currentUser
                    updateUI(user, null)
                } else {
                    Log.w(TAG, "createAccount: failure", task.exception)
                    task.exception?.let { updateUI(null, it) }
                }
            }
    }

    private fun verifyAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signIn: success")
                    val user = auth.currentUser
                    updateUI(user, null)
                    //configTransition()
                    //changeActivity(productSelAct)
                } else {
                    Log.w(TAG, "signIn: failure", task.exception)
                    task.exception?.let { updateUI(null, it) }
                }
            }
    }

    private fun updateUI(user: FirebaseUser?, exception: Exception?) {
        if (exception != null) {
            showMessage(this@MainActivity, exception.message.toString())
        } else {
            showMessage( this@MainActivity,user.toString() + " " + getString(R.string.success))
        }

    }
    companion object {
        private const val TAG = "EmailPassword"
    }
}

