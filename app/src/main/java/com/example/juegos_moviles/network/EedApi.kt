package com.example.juegos_moviles.network

import com.example.juegos_moviles.dto.ChoicesApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EedApi {
    @GET("/eed/fight")
    suspend fun fight(
        @Query("userChoice") userChoice: String
    ) : Response<ChoicesApiResponse>
}