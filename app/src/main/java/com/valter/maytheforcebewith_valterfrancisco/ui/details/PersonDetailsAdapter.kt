package com.valter.maytheforcebewith_valterfrancisco.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.data.model.Detail

class PersonDetailsAdapter : ListAdapter<Detail, PersonDetailsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PersonDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_people_details, parent, false)
    )

    override fun onBindViewHolder(holder: PersonDetailsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Detail>() {
            override fun areItemsTheSame(
                    oldItem: Detail,
                    newItem: Detail
            ) = oldItem.label == newItem.label

            override fun areContentsTheSame(
                    oldItem: Detail,
                    newItem: Detail
            ) = oldItem == newItem
        }
    }
}