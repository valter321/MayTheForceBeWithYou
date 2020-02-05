package com.valter.maytheforcebewith_valterfrancisco.data.model

import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

data class PeopleLoadData(
        val people: List<Person>,
        val nextPage: String?
)