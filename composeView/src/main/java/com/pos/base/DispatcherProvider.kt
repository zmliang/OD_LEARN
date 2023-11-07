package com.pos.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider {
    fun io(): CoroutineDispatcher = Dispatchers.IO

    fun computation(): CoroutineDispatcher = Dispatchers.Default

    fun ui(): CoroutineDispatcher = Dispatchers.Main
}