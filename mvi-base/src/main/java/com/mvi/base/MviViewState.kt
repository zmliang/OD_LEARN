package com.mvi.base

import android.os.Bundle


interface MviViewState


interface MviViewStateSaver<S:MviViewState>{
    fun S.toBundle():Bundle

    fun restore(bundle: Bundle):S
}