package com.valter.maytheforcebewith_valterfrancisco.data.repository

import android.content.Context
import com.valter.maytheforcebewith_valterfrancisco.data.db.PersonDao
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.network.SwapiService
import com.valter.maytheforcebewith_valterfrancisco.utils.isConnectedToNetwork

class SwapiRepositoryImpl(
        private val peopleDao: PersonDao,
        private val swapiService: SwapiService,
        private val context: Context
) : SwapiRepository {

    override suspend fun getPeople(): List<Person> {
        return if (context.applicationContext.isConnectedToNetwork()) {
            swapiService.getPeople().results.also {
                persistData(it)
            }
        } else {
            peopleDao.getAllPersons()
        }
    }

    override suspend fun persistData(people: List<Person>) {
        peopleDao.insert(people)
    }
}