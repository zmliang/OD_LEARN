package com.mvi.base

import kotlinx.coroutines.flow.Flow

interface MviView <I:MviIntent,S:MviViewState,E:MviSingleEvent>{
    fun render(viewState: S)

    fun handleSingleEvent(singleEvent: S)

    fun viewIntents():Flow<I>
}