package com.valter.maytheforcebewith_valterfrancisco.ui

import androidx.navigation.NavController
import com.valter.maytheforcebewith_valterfrancisco.R
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person
import com.valter.maytheforcebewith_valterfrancisco.utils.safeNavigate

class PersonListNavigationImpl(
        private val navController: NavController
) : PersonListNavigation {

    override fun openPersonDetails(person: Person) {
        navController.safeNavigate(
                R.id.action_peopleListFragment_to_PersonDetailsFragment,
                PersonDetailsFragmentArgs(person).toBundle()
        )
    }
}