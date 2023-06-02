package org.bedu.retrofit.api

import org.bedu.retrofit.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WebServices {
    @GET("pokemon/{pokemon}")
    fun getPokemon(@Path("pokemon")pokemon: String): Call<Pokemon>
}