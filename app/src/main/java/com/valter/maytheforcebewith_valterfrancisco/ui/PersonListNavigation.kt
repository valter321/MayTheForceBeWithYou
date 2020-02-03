package com.valter.maytheforcebewith_valterfrancisco.ui

import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

interface PersonListNavigation {

    fun openPersonDetails(person: Person)
}