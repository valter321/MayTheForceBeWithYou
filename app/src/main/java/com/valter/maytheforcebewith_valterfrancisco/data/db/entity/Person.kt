package com.valter.maytheforcebewith_valterfrancisco.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "person")
@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "birth_year")
    val birthYear: String,
    val created: String,
    val edited: String,
    @Json(name = "eye_color")
    val eyeColor: String,
    val films: List<String>?,
    val gender: String,
    @Json(name = "hair_color")
    val hairColor: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    @Json(name = "skin_color")
    val skinColor: String,
    val species: List<String>?,
    val starships: List<String>?,
    val url: String,
    val vehicles: List<String>?
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}