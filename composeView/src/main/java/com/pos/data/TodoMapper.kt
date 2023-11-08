package com.pos.data

import com.pos.repository.entity.Todo
import java.util.UUID
import javax.inject.Inject

class TodoMapper @Inject constructor() {


    fun mapToLocal(todo: Todo) = TodoLocal().apply {
        id = UUID.randomUUID().toString()
        text = todo.text
        isChecked = todo.isChecked
    }

    fun mapFromLocal(todoLocal: TodoLocal) = Todo(
        text = todoLocal.text,
        isChecked = todoLocal.isChecked
    )
}