package org.bedu.network1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.bedu.network1.classes.Event
import org.json.JSONException
import org.json.JSONObject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val baseUrl = "https://data-47065-default-rtdb.firebaseio.com/location.json"
    private val postUrl = "https://data-47065-default-rtdb.firebaseio.com/location/house/d_humidity.json"
    private lateinit var garden: Event
    private lateinit var btnRequest: Button
    private lateinit var btnSincrono: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequest = findViewById(R.id.btnRequest)
        btnSincrono = findViewById(R.id.btnSincrono)
        textView = findViewById(R.id.textView)

        btnRequest.setOnClickListener{
            llamadaAsincrona()
            //postSincrono()
        }

        btnSincrono.setOnClickListener{
            Thread{
                //llamadaSincrona()
                postSincrono()
            }.start()
        }

    }

    fun llamadaAsincrona(){

        //instanciando al cliente
        val okHttpClient = OkHttpClient()

        //obteniendo la url completa
        //val planetNumber = Random.nextInt(1,60) //son 60 planetas
        val url = "$baseUrl"

        //El objeto Request contiene todos los parámetros de la petición (headers, url, body etc)
        val request = Request.Builder()
            .url(url)
            .build()

        //enviando y recibiendo las llamadas de forma asíncrona
        okHttpClient.newCall(request).enqueue(object : Callback {

            //el callback a ejecutar cuando hubo un error
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error",e.toString())
            }

            //el callback a ejectutar cuando obtuvimos una respuesta
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                //Log.d("Response: ", body!!)

                if (body != null)
                garden = extractFeatureFromJson(body)!!

                //Log.d("Response: ", "temperature: $garden.temperature \n humidity: $garden.humidity")

                runOnUiThread{
                    textView.text ="Temperature: ${garden.temperature}" + " " + "Humidity: ${garden.humidity}"
                }
            }
        })
    }

    fun llamadaSincrona(){

        val client = OkHttpClient()

        //obteniendo la url completa

        val url = "$baseUrl"

        val request =  Request.Builder()
            .url(url)
            .build()

        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()
            Log.d("Response: ", body!!)

            //Log.d("Response: ", body!!)

            garden = extractFeatureFromJson(body)!!

            //Log.d("Response: ", "temperature: $garden.temperature \n humidity: $garden.humidity")

            runOnUiThread{
                textView.text ="Temperature: ${garden.temperature}" + " " + "Humidity: ${garden.humidity}"
            }
        } catch (e: Error){
            Log.e("Error",e.toString())
        }
    }

    fun postSincrono() {

        val client = OkHttpClient()


        val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaType()


        //obteniendo la url completa

        val url = "$postUrl"

        val postBody = "59.0"

        val request = Request.Builder()
            .url(url)
            .post(postBody.toRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()


        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            Log.d("Response: ", response.body!!.toString())

            /*     runOnUiThread{
                textView.text ="Temperature: ${garden.temperature}" + " " + "Humidity: ${garden.humidity}"
            }*/
        }
    }

    //Returns an Event object by parsing out information
    private fun extractFeatureFromJson(temperatureJSON: String): Event? {
        try {
            val baseJsonResponse = JSONObject(temperatureJSON)
            //Extract out the house elements
            val houseData = baseJsonResponse.getJSONObject("house")

            //Extract out temperature and humidity
            val temperature = houseData.getDouble("temperature")
            val humidity = houseData.getDouble("humidity")

            //Create a new Event object
            return Event(temperature, humidity)
        } catch (e: JSONException) {
            Log.e("Mensaje", "Problem parsing the event JSON Results", e)
        }
        return null
    }

}


