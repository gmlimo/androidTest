package edu.upbc.firebaseauth


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import edu.upbc.firebaseauth.databinding.FragmentLoginBinding


const val USER_KEY = "USER_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"
const val ISREG_KEY = "ISREG_KEY"

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var fragmentBlankBinding: FragmentLoginBinding ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)
        fragmentBlankBinding = binding

        var user: String? = ""
        var password: String? = ""
        var is_reg: Boolean = false

        binding.userNameText.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                user = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.passText.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = s.toString()
                is_reg = false
            }

            override fun afterTextChanged(s: Editable?) {
                //this@LoginFragment?.requireContext()?.let { showMessage(it, "user: ${user} and password: ${password}" ) }

            }
        })

        binding.loginButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(USER_KEY, user)
            bundle.putString(PASSWORD_KEY, password)
            bundle.putBoolean(ISREG_KEY, is_reg)

            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                putExtras(bundle)
            }

            startActivity(intent)
        }
    }

}