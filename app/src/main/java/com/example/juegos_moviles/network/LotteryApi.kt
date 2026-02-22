package com.example.juegos_moviles.network

import com.example.juegos_moviles.dto.NumberApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LotteryApi {
    @GET("/lottery")
    suspend fun playLottery(
        @Query("attempt") attempt: String,
        @Query("digitLength") digitLength: Int
    ) : Response<NumberApiResponse>
}