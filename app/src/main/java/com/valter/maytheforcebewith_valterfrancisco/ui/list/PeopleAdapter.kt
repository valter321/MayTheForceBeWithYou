package com.valter.maytheforcebewith_valterfrancisco.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

class PeopleAdapter(
        private val onClickPerson: (person: Person) -> Unit,
        private val onClickFavorite: (person: Person) -> Unit
) : PagedListAdapter<Person, PeopleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PeopleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_people_list, parent, false),
            onClickPerson,
            onClickFavorite
    )

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(
                    oldItem: Person,
                    newItem: Person
            ) = oldItem.name == newItem.name

            override fun areContentsTheSame(
                    oldItem: Person,
                    newItem: Person
            ) = oldItem == newItem
        }
    }
}