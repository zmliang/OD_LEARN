package com.pos.base

import com.pos.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


interface UiState

interface UiEvent


abstract class Reducer<S:UiState,E:UiEvent>(initVal:S){
    private val _state:MutableStateFlow<S> = MutableStateFlow(initVal)

    val state:StateFlow<S>
        get() = _state

    val timeCapsule:TimeCapsule<S> = TimeTravelCapsule { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        timeCapsule.addState(initVal)
    }

    fun sendEvent(event:E){
        reduce(_state.value,event)
    }

    fun setState(newState:S){
        val success = _state.tryEmit(newState)
        if (BuildConfig.DEBUG && success){
            timeCapsule.addState(newState)
        }
    }


    abstract fun reduce(oldState:S,event: E)

}