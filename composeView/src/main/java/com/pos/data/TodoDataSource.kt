package com.pos.data

import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

class TodoLocalDataSource @Inject constructor(

) {


    suspend fun getTodos(): List<TodoLocal> {
        //模拟获取数据
        delay(2000)
        return intArrayOf(1,2,3,4,5).map{
            TodoLocal().apply {
                id = UUID.randomUUID().toString()
                text = it.toString()
                isChecked = false
            }
        }
    }

}