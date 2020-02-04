package com.valter.maytheforcebewith_valterfrancisco.data.repository

import android.content.Context
import com.valter.maytheforcebewith_valterfrancisco.data.db.PersonDao
import com.valter.maytheforcebewith_valterfrancisco.data.model.PeopleData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.model.ForceResponse
import com.valter.maytheforcebewith_valterfrancisco.data.network.SwapiService
import com.valter.maytheforcebewith_valterfrancisco.utils.isConnectedToNetwork

class SwapiRepositoryImpl(
        private val peopleDao: PersonDao,
        private val swapiService: SwapiService,
        private val context: Context
) : SwapiRepository {

    override suspend fun getPeople(searchQuery: String?, page: String, isFirstPage: Boolean): PeopleData {

        if(context.applicationContext.isConnectedToNetwork()) {
            if(searchQuery.isNullOrEmpty()) {
                val peopleData = if (isFirstPage) {
                    fetchPeople()
                } else {
                    fetchNextPeople(page)
                }

                val peopleList = peopleData.results.also {
                    persistData(it)
                }

                return PeopleData(
                        peopleList,
                        peopleData.next
                )
            } else {
                val searchList = peopleDao.getPerson("$searchQuery%")
                return PeopleData(
                        searchList,
                        null
                )
            }
        } else {
            return PeopleData(
                    peopleDao.getAllPeople(),
                    null
            )
        }
    }

    private suspend fun fetchPeople() = swapiService.getPeople()

    private suspend fun fetchNextPeople(pageUrl: String) = swapiService.getNextPeople(pageUrl)

    override suspend fun persistData(people: List<Person>) {
        peopleDao.insert(people)
    }

    override suspend fun favoritePerson(person: Person) : ForceResponse {
        return swapiService.sendFavoritePerson(person)
    }
}