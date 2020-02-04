package com.valter.maytheforcebewith_valterfrancisco

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.model.PeopleData
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepository
import com.valter.maytheforcebewith_valterfrancisco.ui.list.MainViewModel
import com.valter.maytheforcebewith_valterfrancisco.utils.Outcome
import com.valter.maytheforcebewith_valterfrancisco.utils.blockingObserve
import com.valter.maytheforcebewith_valterfrancisco.utils.insideValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VerticalViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    @ExperimentalCoroutinesApi
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `When I try to load a page, it should broadcast the loaded people on the LiveData and the quantity of loaded products on its own LiveData`() {
        val peopleList = (1..10).map { mockk<Person>(relaxed = true) }

        val repository: SwapiRepository = mockk {
            coEvery { getPeople(any(), any()) } returns PeopleData(peopleList, null)
        }

        val viewModel = MainViewModel(repository)
        viewModel.peopleData.blockingObserve()
        viewModel.people.blockingObserve()


        assert(viewModel.people.value!!.containsAll(peopleList))
        with(viewModel.peopleData) {
            assert(value is Outcome.Success)
            assert(value.insideValue.people.containsAll(peopleList))
        }
    }

    @Test
    fun `When I try to load a page and an error happens, this error should be triggered on the errors LiveData`() {
        val mockedError: Exception = mockk {
            every { printStackTrace() } returns Unit
        }

        val repository: SwapiRepository = mockk {
            coEvery { getPeople(any(), any()) } throws mockedError
        }

        val viewModel = MainViewModel(repository)

        viewModel.people.blockingObserve()

        assert(viewModel.peopleData.blockingObserve() is Outcome.Failure)
    }
}