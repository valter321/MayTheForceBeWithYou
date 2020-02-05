package com.valter.maytheforcebewith_valterfrancisco.data.network

import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.*
import com.valter.maytheforcebewith_valterfrancisco.data.model.ForceResponse
import com.valter.maytheforcebewith_valterfrancisco.data.network.response.SwapiResponse
import retrofit2.http.*

const val WEB_HOOK_ID = "fcb153e0-804e-4896-b8cc-cb51b2ec4ce7"

interface SwapiService {

    @GET("people")
    suspend fun getPeople() : SwapiResponse<Person>

    @GET
    suspend fun getNextPeople(@Url url: String) : SwapiResponse<Person>

    @POST("https://webhook.site/$WEB_HOOK_ID")
    suspend fun sendFavoritePerson(@Body person: Person) : ForceResponse
}