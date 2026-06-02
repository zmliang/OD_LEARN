package com.north.ws

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import okhttp3.WebSocket


interface IWebSocket {

    fun observe():Flow<SocketEvent?>


    fun send(msg:SocketEvent)


    fun realSocket():WebSocket

}