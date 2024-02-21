package com.arch.fund

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface  UIEvent

interface UIState

abstract class Reducer<E :UIEvent, S:UIState>(initialS:S){

    private val _state = MutableStateFlow(initialS)

    val state: StateFlow<S>
        get() = _state

    val timeCapsule: TimeCapsule<S> = TimeTravelCapsule { storedState ->
        _state.tryEmit(storedState)
    }
    init {
        timeCapsule.addState(initialS)
    }

    fun sendEvent(event:E){
        reducer(_state.value,event)
    }

    fun setState(state:S){
        val succeed = _state.tryEmit(state)
        if (succeed){
            timeCapsule.addState(state)
        }
    }

    abstract fun reducer(oldState:S,event:E)

}


