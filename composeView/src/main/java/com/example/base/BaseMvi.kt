package com.example.base

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




}