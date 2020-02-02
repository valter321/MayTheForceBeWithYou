package com.valter.maytheforcebewith_valterfrancisco.data.network

import com.valter.maytheforcebewith_valterfrancisco.data.network.response.SwapiResponse
import retrofit2.http.GET

interface SwapiService {

    @GET("people")
    suspend fun getPeople() : SwapiResponse
}