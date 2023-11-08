package com.pos.repository

interface CoroutineUseCase<out Output, in Parameters> {

    suspend fun invoke(parameters: Parameters? = null): Output

}