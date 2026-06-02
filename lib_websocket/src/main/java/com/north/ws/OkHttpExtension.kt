package com.north.ws

import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.Request

import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.ws.RealWebSocket
import java.util.Random





fun OkHttpClient.newWebSocket(request: Request,scope: CoroutineScope): IWebSocket {
    val eventListener = SocketEventListener(scope)
    val webSocket = RealWebSocket(
        taskRunner = TaskRunner.INSTANCE,
        originalRequest = request,
        listener = eventListener,
        random = Random(),
        pingIntervalMillis = pingIntervalMillis.toLong(),
        extensions = null, // Always null for clients.
        minimumDeflateSize = minWebSocketMessageToCompress
    )
    webSocket.connect(this)
    return WebSocketImpl(eventListener,webSocket)
}





