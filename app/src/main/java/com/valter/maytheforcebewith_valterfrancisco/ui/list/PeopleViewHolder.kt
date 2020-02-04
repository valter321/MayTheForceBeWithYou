package com.valter.maytheforcebewith_valterfrancisco.ui.list

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.utils.getInitials
import com.valter.maytheforcebewith_valterfrancisco.utils.getRandomMaterialColor
import com.valter.maytheforcebewith_valterfrancisco.utils.setSingleClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_people_list.*

const val TYPE_COLOR = "500"

class PeopleViewHolder(
        override val containerView: View,
        private val personListener: (Person) -> Unit,
        private val favoriteListener: (person: Person) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(person: Person) {
        with(person) {
            imgFavorite.setFavorite(isFavorite)
            imgIcon.setColorFilter(getRandomMaterialColor(containerView.context, TYPE_COLOR))
            txtName.text = name
            txtDescription.text = containerView.context.getString(R.string.date_of_birth, birthYear)
            txtIconName.text = name.getInitials()
            fmrContent.setSingleClickListener { personListener.invoke(this) }
            imgFavorite.setSingleClickListener {
                isFavorite = !isFavorite
                imgFavorite.setFavorite(isFavorite)
                favoriteListener.invoke(this)
            }
        }
    }

    private fun ImageView.setFavorite(isFavorite: Boolean) = setImageResource(if (isFavorite) {
        R.drawable.ic_star_black_24dp
    } else {
        R.drawable.ic_star_border_black_24dp
    })
}