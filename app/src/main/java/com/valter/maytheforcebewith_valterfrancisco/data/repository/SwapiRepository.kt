package com.valter.maytheforcebewith_valterfrancisco.data.repository

import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.PeopleData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

interface SwapiRepository {

    suspend fun getPeople(page: String, isFirstPage: Boolean): PeopleData
    suspend fun persistData(people: List<Person>)
}