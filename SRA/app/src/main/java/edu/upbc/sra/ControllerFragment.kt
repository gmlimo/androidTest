package edu.upbc.sra

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import edu.upbc.sra.data.Event
import edu.upbc.sra.databinding.FragmentControllerBinding
import edu.upbc.sra.utils.extractFeatureFromJson
import edu.upbc.sra.utils.postRequest
import edu.upbc.sra.utils.sendGetRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

private val requestUrl: String =
    "https://garden-21dd2-default-rtdb.firebaseio.com/location.json"
var respuesta: String = ""

class ControllerFragment : Fragment() {
    private var fragmentBlankBinding: FragmentControllerBinding ?= null
    private var plants = arrayOf(" ","Plant 1", "Plant 2", "Plant 3")
    private lateinit var garden: Event

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_controller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentControllerBinding.bind(view)
        fragmentBlankBinding = binding
        var mode = false

        with (binding) {
            controlView.isVisible = false

            autoButton.setOnClickListener {
                controlView.isVisible = false
                mode = true
                statusText.text = getString(R.string.automatic_mode)

                //GET and POST requests
                runBlocking {

                    val task = GlobalScope.launch {
                        //Get new values from http get request
                        //val answer = sendGetRequest(requestUrl)
                        garden = (getTemperatureTask() ?: " ") as Event
                        postRequest(false, false, false, mode)
                    }
                    //Update data
                    task.join()
                    //Write new values
                    statusText.text = getString(R.string.automatic_mode)
                    val temperature = garden.Temperature.toFloat()
                    val moisture1 = garden.Moisture1.toFloat()
                    tempView.text = temperature.toString()
                    humText.text = moisture1.toString()
                }
            }

            manualButton.setOnClickListener {
                controlView.isVisible = true
                mode = false
                var ev1 = false
                var ev2 = false
                var ev3 = false
                statusText.text = getString(R.string.manual_mode)

                //Agregamos Adapter para el click de items
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        //Read plant selection and do something
                        //Read EV state
                        when (position) {
                            1 -> switchButton.setOnCheckedChangeListener{
                                    buttonView, isChecked ->
                                if (isChecked) {
                                    ev1 = true
                                }
                                else ev1 = false
                            }
                            2 -> switchButton.setOnCheckedChangeListener{
                                    buttonView, isChecked ->
                                if (isChecked) {
                                    ev2 = true
                                }
                                else ev2 = false
                            }
                            3 -> switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
                                if (isChecked) {
                                    ev3 = true
                                } else ev3 = false
                            }

                        }

                        updateButton.setOnClickListener {
                            postRequest(ev1, ev2, ev3, mode)
                        }


                        Toast.makeText(this@ControllerFragment?.requireContext(), plants[position], Toast.LENGTH_LONG).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        showDialog(
                            "No seleccionaste la planta",
                            "Vuelve a desplegar la lista y asegúrate de elegir correctamente a alguna"
                        )
                        Toast.makeText(this@ControllerFragment?.requireContext(), "No hay selección", Toast.LENGTH_LONG).show()
                    }
                }
                //Adaptador para mostrar elementos del menú del Spinner
                val menuAdapter = ArrayAdapter(this@ControllerFragment?.requireContext() as Context, android.R.layout.simple_spinner_item, plants)
                spinner.adapter = menuAdapter
            }

            //Plant selection and Turn On and Off irrigation
            }
        }
    fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this@ControllerFragment?.requireContext() as Context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface, which -> dialogInterface.dismiss() }
            .create()
            .show()
    }

    private suspend fun getTemperatureTask(): Event? {

        //Perform HTTP request to the URL and receive a JSON response back

        var jsonResponse: String? = ""
        try {
            jsonResponse = sendGetRequest(requestUrl)
        } catch (e: IOException) {
            Log.e("Mensaje", "Falla en la conexión")
        }

        //Extract relevant fields from JSON response and create an Event object
        val garden: Event? = extractFeatureFromJson(jsonResponse ?: " ")
        return garden
    }

  /*  private fun sendGetRequest(url: String): String {
        val mURL = URL(url) //Get url String and convert it to object

        try {
        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            println("URL : $url")
            println("Response Code : $responseCode")
            //Log.d("URL", "${url}")
            //Log.d("Responde Code", "${responseCode}")

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
                //respuesta = responseCode.toString()
            }
            }
        }
        catch (e: IOException) {
            Log.e("Mensaje", "${respuesta}")
        }
        return respuesta
    }*/

    //Returns an Event object by parsing out information
 /*   private fun extractFeatureFromJson(temperatureJSON: String): Event? {
        try {
            val baseJsonResponse = JSONObject(temperatureJSON)
            //Extract out the house elements
            val houseData = baseJsonResponse.getJSONObject("house")
            //Extract out sensors data
            val sensorData = houseData.getJSONObject("sensors")
            //Extract out temperature and humidity
            val temperature = sensorData.getDouble("temperature")
            val moisture1 = sensorData.getDouble("moisture1")
            val moisture2 = sensorData.getDouble("moisture2")
            val moisture3 = sensorData.getDouble("moisture3")
            val ev1 = sensorData.getBoolean("EV1")
            val ev2 = sensorData.getBoolean("EV2")
            val ev3 = sensorData.getBoolean("EV3")

            //Create a new Event object
            return Event(temperature, moisture1, moisture2, moisture3, ev1, ev2, ev3)
        } catch (e: JSONException) {
            Log.e("Mensaje", "Problem parsing the event JSON Results", e)
        }
        return null
    }*/

  /*  private fun postRequest(ev1: Boolean, ev2: Boolean, ev3: Boolean, mode: Boolean) {
        val client = OkHttpClient()
        // Crear un objeto JSON con el nuevo valor de "dhumidity"
        val json = "{\"EV1\": $ev1, \"EV2\": $ev2, \"EV3\": $ev3, \"Mode\": $mode}"
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)
        // Crear la solicitud PUT con la URL del endpoint y el RequestBody
        val request = Request.Builder()
            .url("https://garden-21dd2-default-rtdb.firebaseio.com/location/house/actuators.json")
            .put(requestBody)
            .build()
        // Ejecutar la solicitud y manejar la respuesta del servidor
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                val responseData = response.body?.string()
                println(responseData)
            }
        })
    }*/
    }



