package com.valter.maytheforcebewith_valterfrancisco.ui.details

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.navigation.fragment.navArgs
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.data.model.Detail
import com.valter.maytheforcebewith_valterfrancisco.ui.components.BaseFragment
import com.valter.maytheforcebewith_valterfrancisco.utils.getInitials
import com.valter.maytheforcebewith_valterfrancisco.utils.getRandomMaterialColor
import kotlinx.android.synthetic.main.fragment_person_details.*

class PersonDetailsFragment : BaseFragment() {

    private val args: PersonDetailsFragmentArgs by navArgs()

    private val baseAdapter = PersonDetailsAdapter()

    override val layout: Int
        get() = R.layout.fragment_person_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedPerson = args.person

        imgIcon.setColorFilter(getRandomMaterialColor(context!!, TYPE_COLOR_DETAILS))

        val details = mutableListOf<Detail>()

        with(selectedPerson) {
            txtIconName.text = name.getInitials()
            txtName.text = name

            details.fillDetails(this)
        }

        rclItems.apply {
            adapter = baseAdapter
            baseAdapter.submitList(details)
        }
    }

    override fun onApplyWindowInsets(p0: View?, insets: WindowInsets): WindowInsets {
        return insets
    }

    override fun onDestroyView() {
        rclItems.adapter = null
        super.onDestroyView()
    }

    private fun MutableList<Detail>.fillDetails(person: Person) {
        add(Detail(getString(R.string.details_height), person.height))
        add(Detail(getString(R.string.details_mass), person.mass))
        add(Detail(getString(R.string.details_hair_color), person.hairColor))
        add(Detail(getString(R.string.details_skin_color), person.skinColor))
        add(Detail(getString(R.string.details_eye_color), person.eyeColor))
        add(Detail(getString(R.string.details_birth_year), person.birthYear))
        add(Detail(getString(R.string.details_gender), person.gender))
    }
}
