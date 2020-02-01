package com.valter.maytheforcebewith_valterfrancisco.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SwapiResponse(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Person>
)