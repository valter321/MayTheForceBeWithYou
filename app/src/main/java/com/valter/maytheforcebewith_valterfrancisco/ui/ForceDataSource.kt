package com.valter.maytheforcebewith_valterfrancisco.ui

import androidx.paging.PageKeyedDataSource

typealias InitialLoader<T> = (callback: PageKeyedDataSource.LoadInitialCallback<String, T>) -> Unit
typealias Loader<T> = (callback: PageKeyedDataSource.LoadCallback<String, T>, pageToLoad: String) -> Unit

class ForceDataSource<T>(private val initialLoader: InitialLoader<T>, private val loader: Loader<T>) : PageKeyedDataSource<String, T>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, T>) {
        initialLoader(callback)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, T>) {
        loader(callback, params.key)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, T>) {}
}