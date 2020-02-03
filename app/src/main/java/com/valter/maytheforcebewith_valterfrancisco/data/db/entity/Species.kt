package com.valter.maytheforcebewith_valterfrancisco.data.db.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Species(
    @Json(name = "average_height")
    val averageHeight: String,
    @Json(name = "average_lifespan")
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    @Json(name = "eye_colors")
    val eyeColors: String,
    val films: List<String>,
    @Json(name = "hair_colors")
    val hairColors: String,
    val homeworld: String,
    val language: String,
    val name: String,
    val people: List<String>,
    @Json(name = "skin_colors")
    val skinColors: String,
    val url: String
)