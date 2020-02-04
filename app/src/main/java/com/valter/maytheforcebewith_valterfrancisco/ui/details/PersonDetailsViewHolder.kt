package com.valter.maytheforcebewith_valterfrancisco.ui.details

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.valter.maytheforcebewith_valterfrancisco.data.model.Detail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_people_details.*

const val TYPE_COLOR_DETAILS = "400"

class PersonDetailsViewHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(detail: Detail) {
        with(detail) {
            txtLabel.text = label
            txtDescription.text = desc
        }
    }
}
