package com.valter.maytheforcebewith_valterfrancisco.ui.list

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.data.model.ErrorData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.ui.components.BaseFragment
import com.valter.maytheforcebewith_valterfrancisco.utils.Outcome
import kotlinx.android.synthetic.main.fragment_people_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@FlowPreview
@ExperimentalCoroutinesApi
class PeopleListFragment : BaseFragment() {

    override val layout: Int
        get() = R.layout.fragment_people_list

    private val viewModel: MainViewModel by viewModel { parametersOf(this) }

    private val navigation: PersonListNavigation by inject { parametersOf(this) }

    private val baseAdapter: PeopleAdapter by lazy { PeopleAdapter(::onEditorialClicked, ::onFavoriteClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tieSearch.addTextChangedListener { newQuery ->
            viewModel.queryChannel.offer(newQuery.toString())
        }

        rclItems.apply {
            adapter = baseAdapter
            val divider = DividerItemDecoration(context, HORIZONTAL)
            context.getDrawable(R.drawable.force_divider)?.let { divider.setDrawable(it) }
            addItemDecoration(divider)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.people.observe(viewLifecycleOwner, Observer {
            baseAdapter.submitList(it)
        })

        viewModel.peopleData.observe(viewLifecycleOwner, Observer { outcome ->
            when (outcome) {
                is Outcome.Progress -> showLoading()
                is Outcome.Success -> showContent()
                is Outcome.Failure -> showError(
                        ErrorData(R.string.error_message,
                                R.string.retry
                        ),
                        ::onRetryClicked
                )
            }
        })

        viewModel.favoriteResponse.observe(viewLifecycleOwner, Observer { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    showContent()
                    Toast.makeText(
                            context,
                            "Favorite Person added",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        rclItems.adapter = null
        super.onDestroyView()
    }

    override fun onApplyWindowInsets(p0: View?, insets: WindowInsets): WindowInsets {
        return insets
    }

    private fun onEditorialClicked(person: Person) {
        navigation.openPersonDetails(person)
    }

    private fun onFavoriteClick(person: Person) {
        viewModel.favoritePerson(person)
    }

    private fun onRetryClicked() {
        viewModel.retry()
    }

}
