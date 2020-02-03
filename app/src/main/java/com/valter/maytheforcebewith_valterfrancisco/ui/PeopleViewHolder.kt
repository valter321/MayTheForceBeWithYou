package com.valter.maytheforcebewith_valterfrancisco.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.utils.getInitials
import com.valter.maytheforcebewith_valterfrancisco.utils.getRandomMaterialColor
import com.valter.maytheforcebewith_valterfrancisco.utils.setSingleClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_people.*

class PeopleViewHolder(
        override val containerView: View,
        private val listener: (String) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(person: Person) {
        with(person) {
            imgIcon.setColorFilter(getRandomMaterialColor(containerView.context, "500"))
            txtName.text = name
            txtDescription.text = "Date of Birth: $birthYear"
            txtIconName.text = name.getInitials()
            fmrContent.setSingleClickListener { listener.invoke(name) }
        }
    }
}