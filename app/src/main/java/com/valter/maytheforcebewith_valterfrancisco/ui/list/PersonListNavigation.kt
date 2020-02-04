package com.valter.maytheforcebewith_valterfrancisco.ui.list

import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

interface PersonListNavigation {

    fun openPersonDetails(person: Person)
}