package com.pos.ui

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestFlow {

    @Test
    fun flow() {
        runBlocking {
            simpleFlow(1000).collect{
                println(it)
            }
        }
    }

    /**
     * 异步返回多个值
     */
    private fun simpleFlow(base:Int): Flow<Int> {
        return flow<Int> {
            for (i in base..12+base) {
                delay(1000)
                emit(i)
            }
        }
    }

}