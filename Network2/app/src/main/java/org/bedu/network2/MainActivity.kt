package org.bedu.network2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.bedu.network2.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val url = "https://swapi.dev/api/people/1/"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnJedi.setOnClickListener{
            llamarALaFuerza(false)
        }

        binding.btnSith.setOnClickListener{
            llamarALaFuerza(true)
        }
    }

    fun llamarALaFuerza(isSith: Boolean = false){

        //instanciando al cliente
        val okHttpClient = OkHttpClient()

        //El objeto Request contiene todos los parámetros de la petición (headers, url, body etc)
        val request = Request.Builder()
            .url(url)
            .build()

        val clientBuilder = okHttpClient.newBuilder()

        //si es sith, permitiremos que el interceptor modifique la url
        if(isSith){
            clientBuilder.addInterceptor(InterceptCharacter())
        }

        clientBuilder.build()
            .newCall(request)
            .enqueue(object : Callback {

                //el callback a ejecutar cuando hubo un error
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Error",e.toString())
                }

                //el callback a ejectutar cuando obtuvimos una respuesta
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    Log.d("Response: ", body!!)

                    try {

                        val jedi = Gson().fromJson(body, InterceptCharacter.Jedi::class.java)

                        println(jedi.toString())

                        runOnUiThread{
                            binding.tvName.text = jedi.name
                            binding.tvHeight.text = jedi.height.toString()
                            binding.tvWeight.text = jedi.mass.toString()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
    }
    }

    class InterceptCharacter : Interceptor {

        //la nueva url que va a sustituir a la anterior
        private val NEW_URL = "https://swapi.dev/api/people/4/"

        //override de la clase Interceptor
        override fun intercept(chain: Interceptor.Chain): Response {

            //creamos un new builder
            val requestBuilder = chain.request().newBuilder()

            //nuevo header agregado por el interceptor
            requestBuilder.addHeader("X-Been","Intercepted");

            //cambiamos la url
            requestBuilder.url(NEW_URL)

            //regresamos el builder modificado
            return chain.proceed(requestBuilder.build())
            //response.newBuilder.body(<a_new_body_response>);
        }

        data class Jedi(
            val name: String? = "",
            val height: Int? = 0,
            val mass: Int? =0
        )
    }







