package com.example.juegos_moviles.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object Network {
    private const val BASE_URL = "https://juegos-back-b39a6e4b40aa.herokuapp.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val eedApi: EedApi by lazy{
        retrofit.create(EedApi::class.java)
    }

    val guessApi: GuessApi by lazy{
        retrofit.create(GuessApi::class.java)
    }

    val lotteryApi: LotteryApi by lazy{
        retrofit.create(LotteryApi::class.java)
    }

}