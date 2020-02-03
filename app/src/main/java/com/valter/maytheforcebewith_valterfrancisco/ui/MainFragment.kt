package com.valter.maytheforcebewith_valterfrancisco.ui

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.PeopleData
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.ui.components.BaseFragment
import com.valter.maytheforcebewith_valterfrancisco.utils.Outcome
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.core.parameter.parametersOf
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override val layout: Int
        get() = R.layout.main_fragment

    private val viewModel: MainViewModel by viewModel { parametersOf(this) }

    private val baseAdapter: PeopleAdapter by lazy { PeopleAdapter(::onEditorialClicked) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rclItems.apply {
            adapter = baseAdapter
            val divider = DividerItemDecoration(context, HORIZONTAL)
            context.getDrawable(R.drawable.force_divider)?.let { divider.setDrawable(it) }
            addItemDecoration(divider)
        }
    }

    private fun setData(data: PeopleData) {
        showContent()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.people.observe(viewLifecycleOwner, Observer {
            baseAdapter.submitList(it)
        })

        viewModel.peopleData.observe(viewLifecycleOwner, Observer { outcome ->
            when (outcome) {
                is Outcome.Progress -> showLoading()
                is Outcome.Success -> setData(outcome.data)
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

    private fun onEditorialClicked(name: String) {
//        navigation.openEditorial(story.type, story.id)
    }

}
