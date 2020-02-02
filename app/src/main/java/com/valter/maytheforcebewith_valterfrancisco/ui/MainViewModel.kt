package com.valter.maytheforcebewith_valterfrancisco.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PageKeyedDataSource
import androidx.paging.toLiveData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.PeopleData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepository
import com.valter.maytheforcebewith_valterfrancisco.utils.Outcome
import kotlinx.coroutines.launch

private const val FIRST_PAGE = "1"

class MainViewModel(
        private val repository: SwapiRepository
) : ViewModel() {

//    private val forceDataSourceFactory = ForceDataSourceFactory {
//        ForceDataSource(this::loadInitialPage, this::loadPage)
//    }

    private var _peopleData = MutableLiveData<Outcome<PeopleData>>()
//    internal var people = forceDataSourceFactory.toLiveData(1)
    private var _people = MutableLiveData<List<Person>>()
    internal var people: LiveData<List<Person>> = _people

    init {
        viewModelScope.launch {
            repository.getPeople().also {
                _people.value = it
            }
        }
    }

//    /**
//     * Proxy to VerticalDataSource
//     */
//    private fun loadInitialPage(callback: PageKeyedDataSource.LoadInitialCallback<String, Person>) {
//        launchLoadRequest(
//                isFirstPage = true,
//                pageToLoad = FIRST_PAGE
//        ) {
//            callback.onResult(it.people, null, it.nextPage)
//        }
//    }
//
//    /**
//     * Proxy to VerticalDataSource
//     */
//    private fun loadPage(
//            callback: PageKeyedDataSource.LoadCallback<String, Person>,
//            pageToLoad: String
//    ) {
//        launchLoadRequest(pageToLoad) {
//            callback.onResult(it.people, it.nextPage)
//        }
//    }

//    private fun launchLoadRequest(
//            pageToLoad: String,
//            isFirstPage: Boolean = false,
//            dataSourceCallbackCaller: (PeopleData) -> Unit
//    ) {
//        viewModelScope.launchWithSafety(
//                _peopleData,
//                loading = isFirstPage,
//                context = dispatchersContainer.IO
//        ) {
//            repository.getPeople(pageToLoad, screen.id, isFirstPage).also {
//                dataSourceCallbackCaller(it)
//            }
//        }
//    }
}
