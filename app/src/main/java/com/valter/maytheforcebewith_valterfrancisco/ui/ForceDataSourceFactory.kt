package com.valter.maytheforcebewith_valterfrancisco.ui

import androidx.paging.DataSource

typealias ForceDataSourceBuilder<T> = () -> DataSource<String, T>
class ForceDataSourceFactory<T>(private val builder: ForceDataSourceBuilder<T>) : DataSource.Factory<String, T>() {

    override fun create(): DataSource<String, T> = builder()
}