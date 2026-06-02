package com.north.ws

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class SocketEventListener(private val scope: CoroutineScope): WebSocketListener() {

    fun send(socketEvent: SocketEvent){
        scope.launch {
            messageChannel.send(socketEvent)
        }
    }
    val messageChannel: Channel<SocketEvent>
        get() = Channel()

    val messageFlow: MutableStateFlow<SocketEvent?>
        get() = MutableStateFlow(null)

     override fun onOpen(webSocket: WebSocket, response: Response) {
        scope.launch {
            for (socketEvent in messageChannel) {
                if (socketEvent is SocketEvent.TxtMessage ){
                    webSocket.send(socketEvent.message)
                }else if (socketEvent is SocketEvent.ByteMessage){
                    webSocket.send(socketEvent.message)
                }
            }
        }
     }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        scope.launch {
            messageFlow.emit(SocketEvent.ByteMessage(bytes))
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        scope.launch {
            messageFlow.emit(SocketEvent.TxtMessage(text))
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {

    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {

    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }

 }
