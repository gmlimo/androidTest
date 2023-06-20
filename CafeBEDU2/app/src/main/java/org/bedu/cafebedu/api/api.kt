package org.bedu.cafebedu.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object api {

    val TIMEOUT_CALL_SECONDS = 5L

    private val client = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cafe-bedu-default-rtdb.firebaseio.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val endpoint = retrofit.create(WebServices::class.java)
}