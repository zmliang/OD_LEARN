package com.zml.nohttp

import java.util.concurrent.ConcurrentLinkedQueue

class RealConnectionPool(

) {


    private val connections = ConcurrentLinkedQueue<RealConnection>()



}