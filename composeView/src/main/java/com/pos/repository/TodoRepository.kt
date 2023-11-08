package com.pos.repository

import com.pos.data.TodoLocalDataSource
import com.pos.data.TodoMapper
import com.pos.repository.entity.Todo
import javax.inject.Inject

interface ITodoRepository {
    suspend fun getTodos(): List<Todo>
}

class TodoRepository @Inject constructor(
    private val todoMapper: TodoMapper,
    private val todoDataSource: TodoLocalDataSource
): ITodoRepository {

    override suspend fun getTodos(): List<Todo> {
        return todoDataSource.getTodos().map(todoMapper::mapFromLocal)
    }

}