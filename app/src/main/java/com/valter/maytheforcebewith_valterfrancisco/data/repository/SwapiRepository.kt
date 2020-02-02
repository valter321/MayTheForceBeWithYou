package com.valter.maytheforcebewith_valterfrancisco.data.repository

import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

interface SwapiRepository {

    suspend fun getPeople(): List<Person>
    suspend fun persistData(people: List<Person>)
}