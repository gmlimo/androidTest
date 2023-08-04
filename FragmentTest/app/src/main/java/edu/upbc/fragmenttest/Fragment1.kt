package edu.upbc.fragmenttest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.upbc.fragmenttest.databinding.Fragment1Binding

const val NAME = "NAME"
const val OCCUPATION = "OCCUPATION"

class Fragment1 : Fragment(R.layout.fragment_1) {

   private var fragmentBlankBinding: Fragment1Binding ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = Fragment1Binding.bind(view)
        fragmentBlankBinding = binding

        with (binding) {
            name.text = "Candy Crusher "
            occupation.text = "Guardian Version 2"
        }
    }


    companion object {

      /*  @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }*/
    }
}