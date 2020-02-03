package com.valter.maytheforcebewith_valterfrancisco.data.network

import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.*
import com.valter.maytheforcebewith_valterfrancisco.data.network.response.SwapiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SwapiService {

    @GET("people")
    suspend fun getPeople(
            @Query("page") page: String = ""
    ) : SwapiResponse<Person>

    @GET
    suspend fun getNextPeople(@Url url: String) : SwapiResponse<Person>

    @GET("planets")
    suspend fun getPlanets() : SwapiResponse<Planet>

    @GET("films")
    suspend fun getFilms() : SwapiResponse<Film>

    @GET("species")
    suspend fun getSpecies() : SwapiResponse<Species>

    @GET("vehicles")
    suspend fun getVehicles() : SwapiResponse<Vehicle>

    @GET("starships")
    suspend fun getStarships() : SwapiResponse<Starship>
}