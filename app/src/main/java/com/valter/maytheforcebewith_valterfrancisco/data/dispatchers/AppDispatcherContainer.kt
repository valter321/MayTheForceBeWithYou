package com.valter.maytheforcebewith_valterfrancisco.data.dispatchers

import kotlinx.coroutines.Dispatchers

/**
 * DispatcherContainer for the app
 */
class AppDispatchersContainer : DispatchersContainer {
    override val Default = Dispatchers.Default
    override val Main = Dispatchers.Main
    override val Unconfined = Dispatchers.Unconfined
    override val IO = Dispatchers.IO
}