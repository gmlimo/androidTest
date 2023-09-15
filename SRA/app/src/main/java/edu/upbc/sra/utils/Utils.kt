package edu.upbc.sra.utils

import android.Manifest.permission.READ_PHONE_STATE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import edu.upbc.sra.data.Event
import edu.upbc.sra.respuesta
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun hasPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        READ_PHONE_STATE ) == PackageManager.PERMISSION_GRANTED
}

fun AppCompatActivity.askPermission(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(arrayOf(READ_PHONE_STATE), 1)
    }
}

fun sendGetRequest(url: String): String {
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
        Log.e("Mensaje", "$respuesta")
    }
    return respuesta
}

//Returns an Event object by parsing out information
fun extractFeatureFromJson(temperatureJSON: String): Event? {
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
}

fun postRequest(ev1: Boolean, ev2: Boolean, ev3: Boolean, mode: Boolean) {
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
}

