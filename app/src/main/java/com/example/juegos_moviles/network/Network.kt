package com.example.juegos_moviles.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object Network {
    private const val BASE_URL = "https://juegos-back-b39a6e4b40aa.herokuapp.com/"

    val api: JuegosBackAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JuegosBackAPI::class.java)
    }
}