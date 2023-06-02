package org.bedu.retrofit2.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api{
    private val loggingInterceptor = HttpLoggingInterceptor()

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()


    val loginService: LoginService =
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)

}