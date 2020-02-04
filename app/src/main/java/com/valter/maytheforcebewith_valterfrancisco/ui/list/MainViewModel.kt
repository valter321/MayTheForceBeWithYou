package com.valter.maytheforcebewith_valterfrancisco.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PageKeyedDataSource
import androidx.paging.toLiveData
import com.valter.maytheforcebewith_valterfrancisco.data.model.ForceResponse
import com.valter.maytheforcebewith_valterfrancisco.data.model.PeopleData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepository
import com.valter.maytheforcebewith_valterfrancisco.ui.components.ForceDataSource
import com.valter.maytheforcebewith_valterfrancisco.ui.components.ForceDataSourceFactory
import com.valter.maytheforcebewith_valterfrancisco.utils.Outcome
import com.valter.maytheforcebewith_valterfrancisco.utils.launchSafely
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val QUERY_DEBOUNCE = 500L

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(
        private val repository: SwapiRepository
) : ViewModel() {

    private val forceDataSourceFactory = ForceDataSourceFactory {
        ForceDataSource(this::loadInitialPage, this::loadPage)
    }

    val queryChannel = ConflatedBroadcastChannel<String>()

    private var _peopleData = MutableLiveData<Outcome<PeopleData>>()
    internal var peopleData: LiveData<Outcome<PeopleData>> = _peopleData
    internal var people = forceDataSourceFactory.toLiveData(1)

    private var _favoriteResponse = MutableLiveData<Outcome<ForceResponse>>()
    var favoriteResponse: LiveData<Outcome<ForceResponse>> = _favoriteResponse

    init {
        queryChannel.asFlow()
                .debounce(QUERY_DEBOUNCE)
                .onEach { retry() }
                .launchIn(viewModelScope)
    }

    /**
     * Proxy to ForceDataSource
     */
    private fun loadInitialPage(callback: PageKeyedDataSource.LoadInitialCallback<String, Person>) {
        launchLoadRequest(
                isFirstPage = true
        ) {
            callback.onResult(it.people, null, it.nextPage)
        }
    }

    /**
     * Proxy to ForceDataSource
     */
    private fun loadPage(
            callback: PageKeyedDataSource.LoadCallback<String, Person>,
            pageToLoad: String
    ) {
        launchLoadRequest(pageToLoad) {
            callback.onResult(it.people, it.nextPage)
        }
    }

    private fun launchLoadRequest(
            pageToLoad: String = "",
            isFirstPage: Boolean = false,
            dataSourceCallbackCaller: (PeopleData) -> Unit
    ) {
        viewModelScope.launchSafely(
                _peopleData,
                loading = isFirstPage,
                context = Dispatchers.IO
        ) {
            repository.getPeople(queryChannel.valueOrNull.orEmpty(), pageToLoad, isFirstPage).also {
                dataSourceCallbackCaller(it)
            }
        }
    }

    fun favoritePerson(person: Person) {
        viewModelScope.launchSafely(
                _favoriteResponse,
                loading = false,
                context = Dispatchers.IO
        ) {
            repository.favoritePerson(person)
        }
    }

    fun retry() {
        people.value?.dataSource?.invalidate()
    }
}
