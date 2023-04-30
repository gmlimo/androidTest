package org.bedu.aistest1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bedu.aistest1.classes.Event
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private val requestUrl: String =
    "https://data-47065-default-rtdb.firebaseio.com/location.json"
private var respuesta: String = ""

class AIS_Main : AppCompatActivity() {

    //Variables globales
    private lateinit var spinner: Spinner
    var plants = arrayOf("Plant 1", "Plant 2", "Plant 3")
    private lateinit var controlView: View
    private lateinit var autoButton: ImageButton
    private lateinit var manualButton: ImageButton
    private lateinit var plusButton: Button
    private lateinit var minusButton: Button
    private lateinit var humText: TextView
    private lateinit var tempText: TextView
    private lateinit var humTextF: TextView

    private lateinit var garden: Event

    private var quantity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ais_main)

        //Enlace de las vistas
        controlView = findViewById(R.id.controlView)
        spinner = findViewById(R.id.spinner)
        autoButton = findViewById(R.id.autoButton)
        manualButton = findViewById(R.id.manualButton)
        plusButton = findViewById(R.id.plusButton)
        minusButton = findViewById(R.id.minusButton)
        humText = findViewById(R.id.humCon_Text)
        tempText = findViewById(R.id.tempView)
        humTextF = findViewById(R.id.humText)

        //Se esconde el control manual
        controlView.isVisible = false

        //OnClick Listener para el botón de Auto
        autoButton.setOnClickListener {
            controlView.isVisible = false

            runBlocking {

                val task = GlobalScope.launch {
                    garden = getTemperatureTask()!!
                }

                task.join()
                tempText.text = garden?.temperature.toString()
                humTextF.text = garden?.humidity.toString()
            }
        }

        //OnClick Listener para el botón de Manual
        manualButton.setOnClickListener {
            controlView.isVisible = true
        }

        //On ClickListener for the Plus Button
        plusButton.setOnClickListener {
            quantity++
            if(quantity > 100) {
                quantity = 100
                Toast.makeText(this, "Humidity can´t be more than 100%", Toast.LENGTH_SHORT).show()
            }
            humText.text = quantity.toString()
        }

        //On ClickListener for the Minus Button
        minusButton.setOnClickListener {
            quantity--
            if(quantity < 0) {
                quantity = 0
                Toast.makeText(this, "Humidity can´t be less than 0%", Toast.LENGTH_SHORT).show()
            }
            humText.text = quantity.toString()
        }


        //Agregamos Adapter para el click de items
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@AIS_Main, plants[position], Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showDialog(
                    "No seleccionaste la planta",
                    "Vuelve a desplegar la lista y asegúrate de elegir correctamente a alguna"
                )
                Toast.makeText(applicationContext, "No hay selección", Toast.LENGTH_LONG).show()
            }
        }
        //Adaptador para mostrar elementos del menú del Spinner
        val menuAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, plants)
        spinner.adapter = menuAdapter
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface, which -> dialogInterface.dismiss() }
            .create()
            .show()
    }

    suspend fun getTemperatureTask(): Event? {

        //Perform HTTP request to the URL and receive a JSON response back

        var jsonResponse: String? = ""
        try {
            jsonResponse = sendGetRequest(requestUrl)
        } catch (e: IOException) {
            Log.e("Mensaje", "Falla en la conexión")
        }


        //Extract relevant fields from JSON response and create an Event object
        val garden: Event? = extractFeatureFromJson(jsonResponse!!)
        return garden
    }

    fun sendGetRequest(url: String): String {

        val mURL = URL(url) //Get url String and convert it to object

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuilder()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
                respuesta = response.toString()
            }
        }
        return respuesta
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

