package com.example.juegos_moviles.network

import com.example.juegos_moviles.dto.ChoicesApiResponse
import com.example.juegos_moviles.dto.NumberApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JuegosBackAPI {

    @GET("/eed/fight")
    suspend fun fight(
        @Query("userChoice") userChoice: String
    ) : Response<ChoicesApiResponse>

    @GET("/lottery")
    suspend fun playLottery(
        @Query("attempt") attempt: String,
        @Query("digitLength") digitLength: Int
    ) : Response<NumberApiResponse>

    @GET("/guess")
    suspend fun guessNumber(
        @Query("attempt") attempt: Int,
        @Query("maxNum") maxNum: Int
    ) : Response<NumberApiResponse>
}