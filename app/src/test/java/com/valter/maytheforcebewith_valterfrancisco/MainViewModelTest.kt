package com.valter.maytheforcebewith_valterfrancisco

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.dispatchers.TestDispatcherContainer
import com.valter.maytheforcebewith_valterfrancisco.data.model.ForceResponse
import com.valter.maytheforcebewith_valterfrancisco.data.model.PeopleLoadData
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepository
import com.valter.maytheforcebewith_valterfrancisco.ui.list.MainViewModel
import com.valter.maytheforcebewith_valterfrancisco.utils.Outcome
import com.valter.maytheforcebewith_valterfrancisco.utils.blockingObserve
import com.valter.maytheforcebewith_valterfrancisco.utils.insideValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
class VerticalViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this)
    }

    @Test
    fun `Loading a page, should broadcast the loaded people on the LiveData`() {
        val peopleList = (1..10).map { mockk<Person>(relaxed = true) }

        val mockedPeopleData = PeopleLoadData(peopleList, null)

        val repository: SwapiRepository = mockk {
            coEvery { getPeople(any(), any(), any()) } returns mockedPeopleData
        }

        val viewModel = MainViewModel(repository, TestDispatcherContainer)
        viewModel.peopleData.blockingObserve()
        viewModel.people.blockingObserve()

        assert(viewModel.people.value!!.containsAll(peopleList))
        with(viewModel.peopleData) {
            assert(value is Outcome.Success)
            assert(value.insideValue.people.containsAll(peopleList))
        }
    }

    @Test
    fun `Loading one page and then another, should broadcast both pages on the LiveData`() {
        val pageList1 = (1..10).map { mockk<Person>(relaxed = true) }

        val pageList2 = listOf(mockk<Person>(relaxed = true))

        val repository: SwapiRepository = mockk {
            coEvery { getPeople(any(), any(), any()) } returns PeopleLoadData(pageList1, "nextPage")
            coEvery { getPeople(any(),"nextPage", any()) } returns PeopleLoadData(pageList2, null)
        }

        val viewModel = MainViewModel(repository, TestDispatcherContainer)

        viewModel.people.blockingObserve()

        with(viewModel.people.value!!) {
            assert(containsAll(pageList1)).also { loadAround(pageList1.size - 1) }
            assert(containsAll(pageList2))
            assert(size == pageList1.size + pageList2.size)
        }
    }

    @Test
    fun `If an error occurs when loading a page, this error should be triggered on the LiveData`() {
        val mockedError: Exception = mockk {
            every { printStackTrace() } returns Unit
        }

        val repository: SwapiRepository = mockk {
            coEvery { getPeople(any(), any(), any()) } throws mockedError
        }

        val viewModel = MainViewModel(repository, TestDispatcherContainer)

        viewModel.people.blockingObserve()

        assert(viewModel.peopleData.blockingObserve() is Outcome.Failure)
    }

    @Test
    fun `Choosing a favorite, should broadcast the response to the LiveData`() {
        val person = mockk<Person>(relaxed = true)

        val response = ForceResponse("200")

        val repository: SwapiRepository = mockk {
            coEvery { favoritePerson(person) } returns response
        }

        val viewModel = MainViewModel(repository, TestDispatcherContainer)
        viewModel.favoritePerson(person)
        viewModel.favoriteResponse.blockingObserve()

        assert(viewModel.favoriteResponse.value!!.insideValue == response)
    }

    @Test
    fun `If an error occurs when choosing favorite, this error should be triggered on the LiveData`() {
        val person = mockk<Person>(relaxed = true)

        val mockedError: Exception = mockk {
            every { printStackTrace() } returns Unit
        }

        val repository: SwapiRepository = mockk {
            coEvery { favoritePerson(any()) } throws mockedError
        }

        val viewModel = MainViewModel(repository, TestDispatcherContainer)
        viewModel.favoritePerson(person)
        viewModel.favoriteResponse.blockingObserve()

        assert(viewModel.favoriteResponse.blockingObserve() is Outcome.Failure)
    }
}