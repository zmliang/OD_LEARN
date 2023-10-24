package com.zml.nohttp

import java.lang.ref.Reference
import java.net.Socket

class RealConnection:Connection {
    override fun socket(): Socket {
        TODO("Not yet implemented")
    }

    override fun protocol(): String {
        TODO("Not yet implemented")
    }

    val calls = mutableListOf<Reference<RealCall>>();

    fun newCodec( client: NoHttpClient,
                  chain: RealInterceptorChain):ExchangeCodec{

        return Http1Codec(this)
    }

}