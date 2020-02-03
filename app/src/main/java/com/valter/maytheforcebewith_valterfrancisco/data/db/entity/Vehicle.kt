package com.valter.maytheforcebewith_valterfrancisco.data.db.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Vehicle(
    @Json(name = "cargo_capacity")
    val cargoCapacity: String,
    val consumables: String,
    @Json(name = "cost_in_credits")
    val costInCredits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val length: String,
    val manufacturer: String,
    @Json(name = "max_atmosphering_speed")
    val maxAtmospheringSpeed: String,
    val model: String,
    val name: String,
    val passengers: String,
    val pilots: List<Any>,
    val url: String,
    @Json(name = "vehicle_class")
    val vehicleClass: String
)