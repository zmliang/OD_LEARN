package com.pos.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    class ProducerScope<T>{
        suspend fun produce(t:T){
            println("t = $t")
        }
    }

    fun <R,T> launchCoroutine(receiver:R,block: suspend R.()->T){
        block.startCoroutine(receiver = receiver,object :Continuation<T>{
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<T>) {
                println("continuation end with $result")
            }

        })
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        println("========= start ===============")
        GlobalScope.launch {
            println("global scope start")
            test1()
            test2()

            val deferred =async {
                delay(1000)
                "大傻叉"
            }

            val result = deferred.await()

            println("this is in global scope==$result")
        }
        println("=========================")
    }




@Test
fun test_continuation(){
        suspend {
            print("这是我的测试输出\n")
        }.startCoroutine(object: Continuation<Any>{
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Any>) {
                print("这是resumeWith方法 = $result")
            }

        })
}

    suspend fun test1(){
        println("this is test1")
    }

    suspend fun test2(){
        println("this is test2")
    }


}