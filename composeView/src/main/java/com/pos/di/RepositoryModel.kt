package com.pos.di

import com.pos.repository.GetTodosUseCase
import com.pos.repository.IGetTodosUseCase
import com.pos.repository.ITodoRepository
import com.pos.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindTodoRepository(repository: TodoRepository): ITodoRepository

}

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    @ViewModelScoped
    fun bindTodosUseCase(todosUseCase: GetTodosUseCase): IGetTodosUseCase

}