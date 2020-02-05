package com.valter.maytheforcebewith_valterfrancisco.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersContainer {
    val Default: CoroutineDispatcher
    val Main: CoroutineDispatcher
    val Unconfined: CoroutineDispatcher
    val IO: CoroutineDispatcher
}
