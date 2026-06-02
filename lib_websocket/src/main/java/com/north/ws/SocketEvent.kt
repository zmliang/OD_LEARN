package com.north.ws

import okio.ByteString

sealed class SocketEvent{
     data class Open( val reopen:Boolean):SocketEvent()
     data class TxtMessage( val message:String):SocketEvent()

     data class ByteMessage( val message:ByteString):SocketEvent()

    data class Closed( val exception:String):SocketEvent()


 }
