package com.valter.maytheforcebewith_valterfrancisco.data.db.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Starship(
    @Json(name = "cargo_capacity")
    val cargoCapacity: String,
    val consumables: String,
    @Json(name = "cost_in_credits")
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    @Json(name = "hyperdrive_rating")
    val hyperdriveRating: String,
    val length: String,
    @Json(name = "MGLT")
    val mGLT: String,
    val manufacturer: String,
    @Json(name = "max_atmosphering_speed")
    val maxAtmospheringSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<Any>,
    @Json(name = "starship_class")
    val starshipClass: String,
    val url: String
)