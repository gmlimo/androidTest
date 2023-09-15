package edu.upbc.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.upbc.firebaseauth.databinding.FragmentRegisterBinding
import edu.upbc.firebaseauth.utils.changeFragment

const val REG_USER_KEY = "REG_USER_KEY"
const val REG_PASSWORD_KEY = "REG_PASSWORD_KEY"

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var fragmentBlankBinding: FragmentRegisterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRegisterBinding.bind(view)
        fragmentBlankBinding = binding

        binding.signInText.setOnClickListener {
            changeFragment(R.id.containerView, loginFragment)
        }

        var user: String? = ""
        var password: String? = ""
        var is_reg: Boolean = false

        binding.emailText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                user = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.regPass.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = s.toString()
                is_reg = true
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.registerButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(REG_USER_KEY, user)
            bundle.putString(REG_PASSWORD_KEY, password)
            bundle.putBoolean(ISREG_KEY, is_reg)

            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                putExtras(bundle)
            }

            startActivity(intent)
        }

    }

}