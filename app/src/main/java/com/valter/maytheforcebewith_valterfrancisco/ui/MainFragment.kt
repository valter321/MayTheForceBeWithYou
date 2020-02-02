package com.valter.maytheforcebewith_valterfrancisco.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.utils.Outcome
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.core.parameter.parametersOf
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.people.observe(viewLifecycleOwner, Observer { outcome ->
            when (outcome) {
                is Outcome.Success -> message.text = outcome.data.toString()
            }

        })
    }

}
