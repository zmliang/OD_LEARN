package com.arch.fund

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<E:UIEvent,S:UIState> :ViewModel(){


    abstract val state: Flow<S>

}