package com.zml.nohttp

import java.net.Socket

interface Connection {



    fun socket():Socket


    fun protocol():String

}