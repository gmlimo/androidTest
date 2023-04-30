package org.bedu.coroutines

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bedu.coroutines.jvm.Event
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset

private val requestUrl: String =
    "https://data-47065-default-rtdb.firebaseio.com/location.json"
var respuesta: String = ""

class MainActivity : AppCompatActivity() {

    private lateinit var temperature: TextView
    private lateinit var humidity: TextView
    private lateinit var boton: Button

    private lateinit var garden: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        temperature = findViewById(R.id.temperature)
        humidity = findViewById(R.id.humidity)
        boton = findViewById(R.id.button)


        //val scope = GlobalScope.launch {  }

//runBlocking {

    val task = GlobalScope.launch {
        garden = getTemperatureTask()!!
    }

    boton.setOnClickListener {
runBlocking {
        val task = GlobalScope.launch {
            garden = getTemperatureTask()!!
        }
            task.join()
            temperature.text = garden?.temperature.toString()
            humidity.text = garden?.humidity.toString()

    }
}

    }
}

suspend fun getTemperatureTask(): Event? {

    //Perform HTTP request to the URL and receive a JSON response back

    var jsonResponse: String? = ""
    try {
        jsonResponse = sendGetRequest(requestUrl)
    } catch (e: IOException) {
        //Handle the IO Exception
    }


    //Extract relevant fields from JSON response and create an Event object
    val garden: Event? = extractFeatureFromJson(jsonResponse!!)
    return garden
}

//Make HTTP request to the given URL and return a String as the response

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