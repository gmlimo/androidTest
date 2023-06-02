package org.bedu.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import org.bedu.retrofit.api.Api
import org.bedu.retrofit.databinding.ActivityMainBinding
import org.bedu.retrofit.model.Pokemon
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding) {
            btnSearch.setOnClickListener {
                val pokemonName = etPokemon.text.toString()
                val call = Api.endpoint.getPokemon(pokemonName)

                call.enqueue(object : Callback<Pokemon> {
                    override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Hubo un error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                        if(response.code()==200){
                            val body = response.body()
                            Log.e("Respuesta","${response.body().toString()}")

                            binding.tvPokemon.text = body?.name
                            binding.tvWeight.text = "peso: " + body?.weight.toString()
                            Picasso.get().load(body?.sprites?.photoUrl).into(binding.pokemon);
                        } else{
                            Log.e("Not200","Error not 200: $response")
                            Toast.makeText(this@MainActivity, "No se encontró el pokemon", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }
        }

    }
}