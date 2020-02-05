package com.valter.maytheforcebewith_valterfrancisco.data.repository

import android.content.Context
import com.valter.maytheforcebewith_valterfrancisco.data.db.PersonDao
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.model.ForceResponse
import com.valter.maytheforcebewith_valterfrancisco.data.model.PeopleLoadData
import com.valter.maytheforcebewith_valterfrancisco.data.network.SwapiService
import com.valter.maytheforcebewith_valterfrancisco.utils.isConnectedToNetwork

class SwapiRepositoryImpl(
        private val peopleDao: PersonDao,
        private val swapiService: SwapiService,
        context: Context
) : SwapiRepository {

    private val appContext = context.applicationContext

    override suspend fun getPeople(searchQuery: String?, page: String, isFirstPage: Boolean) = if (appContext.isConnectedToNetwork()) {
        if (searchQuery.isNullOrEmpty()) {
            fetchPeopleInfoFromNetwork(page, isFirstPage)
        } else {
            filterPeople(searchQuery)
        }
    } else {
        fetchPeopleInfoFromDatabase()
    }

    private suspend fun fetchPeopleInfoFromNetwork(page: String, isFirstPage: Boolean): PeopleLoadData {
        val peopleData = if (isFirstPage) {
            fetchPeople()
        } else {
            fetchNextPeople(page)
        }

        return PeopleLoadData(
                peopleData.results,
                peopleData.next
        ).also {
            persistData(it.people)
        }
    }

    private suspend fun filterPeople(searchQuery: String) = peopleDao.getPerson("$searchQuery%").toPeopleLoadData()

    private suspend fun fetchPeopleInfoFromDatabase() = peopleDao.getAllPeople().toPeopleLoadData()

    private suspend fun fetchPeople() = swapiService.getPeople()

    private suspend fun fetchNextPeople(pageUrl: String) = swapiService.getNextPeople(pageUrl)

    override suspend fun persistData(people: List<Person>) = peopleDao.insert(people)

    override suspend fun favoritePerson(person: Person) = swapiService.sendFavoritePerson(person)

    private fun List<Person>.toPeopleLoadData(nextPage: String? = null) = PeopleLoadData(this, nextPage)
}