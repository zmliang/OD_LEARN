package com.north.ws

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.WebSocket
import okhttp3.internal.ws.RealWebSocket

class WebSocketImpl(private val eventListener: SocketEventListener,private val realWebSocket: RealWebSocket):IWebSocket {

    override fun observe(): Flow<SocketEvent?> {
        return eventListener.messageFlow.asStateFlow()
    }

    override fun send(msg: SocketEvent) {
        eventListener.send(msg)
    }


    override fun realSocket(): WebSocket {
        return realWebSocket
    }


}