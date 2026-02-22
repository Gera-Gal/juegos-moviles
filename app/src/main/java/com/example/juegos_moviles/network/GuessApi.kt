package com.example.juegos_moviles.network

import com.example.juegos_moviles.dto.NumberApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GuessApi {
    @GET("/guess")
    suspend fun guessNumber(
        @Query("attempt") attempt: Int,
        @Query("maxNum") maxNum: Int
    ) : Response<NumberApiResponse>
}