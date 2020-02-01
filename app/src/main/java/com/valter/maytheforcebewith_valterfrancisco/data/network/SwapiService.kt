package com.valter.maytheforcebewith_valterfrancisco.data.network

import com.valter.maytheforcebewith_valterfrancisco.data.SwapiResponse
import retrofit2.http.GET

interface SwapiService {

    @GET("people")
    suspend fun getAllPersons() : SwapiResponse
}