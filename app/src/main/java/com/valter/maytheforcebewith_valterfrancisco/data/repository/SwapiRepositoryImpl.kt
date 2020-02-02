package com.valter.maytheforcebewith_valterfrancisco.data.repository

import com.valter.maytheforcebewith_valterfrancisco.data.db.PersonDao
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.network.SwapiService

class SwapiRepositoryImpl(
        private val peopleDao: PersonDao,
        private val swapiService: SwapiService
) : SwapiRepository {

    override suspend fun getPeople(): List<Person> {
        return swapiService.getPeople().results.also {
            persistData(it)
        }
    }

    override suspend fun persistData(people: List<Person>) {
        peopleDao.insert(people)
    }
}