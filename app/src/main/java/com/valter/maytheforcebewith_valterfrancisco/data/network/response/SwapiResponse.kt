package com.valter.maytheforcebewith_valterfrancisco.data.network.response

import com.squareup.moshi.JsonClass
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

@JsonClass(generateAdapter = true)
data class SwapiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Person>
)