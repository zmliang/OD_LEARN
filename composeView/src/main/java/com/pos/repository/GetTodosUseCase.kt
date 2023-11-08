package com.pos.repository

import com.pos.repository.entity.Todo
import javax.inject.Inject

interface IGetTodosUseCase: CoroutineUseCase<List<Todo>, Unit>

class GetTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
): IGetTodosUseCase {

    override suspend fun invoke(parameters: Unit?): List<Todo> {
        return todoRepository.getTodos()
    }
}