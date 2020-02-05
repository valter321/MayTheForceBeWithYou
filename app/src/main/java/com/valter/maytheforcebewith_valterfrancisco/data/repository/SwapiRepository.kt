package com.valter.maytheforcebewith_valterfrancisco.data.repository

import com.valter.maytheforcebewith_valterfrancisco.data.model.ForceResponse
import com.valter.maytheforcebewith_valterfrancisco.data.model.PeopleLoadData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

interface SwapiRepository {

    suspend fun getPeople(searchQuery: String? = null, page: String, isFirstPage: Boolean): PeopleLoadData
    suspend fun persistData(people: List<Person>)
    suspend fun favoritePerson(person: Person) : ForceResponse
}