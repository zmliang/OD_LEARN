package com.mvi.base

import android.os.Build
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger
import androidx.lifecycle.viewModelScope

abstract class AbstractMviViewModel<I:MviIntent,S:MviViewState,E:MviSingleEvent> :
ViewModel(),MviViewModel<I,S,E>{
    protected open val tag:String?=  null

    protected val logTag by lazy(LazyThreadSafetyMode.PUBLICATION) {
        (tag?:this::class.java.simpleName).let {
            if (it.length<= MAX_TAG_LENGTH || Build.VERSION.SDK_INT>=26){
                it
            }else{
                it.take(MAX_TAG_LENGTH)
            }
        }
    }
    private companion object{
        private const val SubscriberBufferSize = 64
        private const val MAX_TAG_LENGTH = 23
    }


    private val eventChannel = Channel<E>(Channel.UNLIMITED)

    private val intentMutableFlow = MutableSharedFlow<I>(extraBufferCapacity = SubscriberBufferSize)

    final override val singleEvent: Flow<E> = eventChannel.receiveAsFlow()

    final override suspend fun processIntent(intent: I) = intentMutableFlow.emit(intent)


    @CallSuper
    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
        Timber.tag(logTag).d("onCleared")
    }

    protected suspend fun sendEvent(event:E){
        //debugCheckImmediateMainDispatcher()
        eventChannel.trySend(event)
            .onFailure { Timber.tag(logTag).e(it,"Failed to send event:$event") }
            .getOrThrow()
    }

    protected val intentShareFlow:SharedFlow<I> get() = intentMutableFlow

    protected fun <T> Flow<T>.debugLog(subject: String): Flow<T> =
        if (BuildConfig.DEBUG) {
            onEach { Timber.tag(logTag).d(">>> $subject: $it") }
        } else {
            this
        }


    protected fun <T> SharedFlow<T>.debugLog(subject: String): SharedFlow<T> =
        if (BuildConfig.DEBUG) {
            val self = this

            object : SharedFlow<T> by self {
                val subscriberCount = AtomicInteger(0)

                override suspend fun collect(collector: FlowCollector<T>): Nothing {
                    val count = subscriberCount.getAndIncrement()

                    self.collect {
                        Timber.tag(logTag).d(">>> $subject ~ $count: $it")
                        collector.emit(it)
                    }
                }
            }
        } else {
            this
        }

    protected fun <T> Flow<T>.shareWhileSubscribed(): SharedFlow<T> = shareIn(viewModelScope, SharingStarted.WhileSubscribed())

}