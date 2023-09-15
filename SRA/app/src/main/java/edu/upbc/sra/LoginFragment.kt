package edu.upbc.sra


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.upbc.sra.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {
    private var fragmentBlankBinding: LoginFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = LoginFragmentBinding.bind(view)
        fragmentBlankBinding = binding

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.itemFragment)
        }
    }
}