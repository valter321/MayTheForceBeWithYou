package com.valter.maytheforcebewith_valterfrancisco.data.db.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Planet(
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: List<String>,
    val gravity: String,
    val name: String,
    @Json(name = "orbital_period")
    val orbitalPeriod: String,
    val population: String,
    val residents: List<String>,
    @Json(name = "rotation_period")
    val rotationPeriod: String,
    @Json(name = "surface_water")
    val surfaceWater: String,
    val terrain: String,
    val url: String
)