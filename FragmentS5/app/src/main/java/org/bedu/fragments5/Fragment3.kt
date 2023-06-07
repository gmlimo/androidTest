package org.bedu.fragments5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.bedu.fragments5.databinding.Fragment3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment(R.layout.fragment_3) {

    private var fragmentBlankBinding: Fragment3Binding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Con esto se puede llenar los campos en blanco de la interfaz generada en el fragment_3.xml
        val binding = Fragment3Binding.bind(view)
        fragmentBlankBinding = binding
        binding.txtDriverIdData.text = "Texto completo de prueba"
        binding.txtDriverRfcData.text = "RFC_Hola"

        //Para recibir datos a través de Shared Preferences
        val pref = activity?.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val idTaxista = pref?.getInt("idConductor", 0) //Así sobreescribiría un valor
        val idDriver = arguments?.getInt("idConductor", 0)
        val accessToken = arguments?.getString("token", "")

        binding.txtDriverLicenceData.text = accessToken
        binding.txtDriverNameData.text = idDriver.toString()
    }
}