package ru.zerogravity.gtdify.model.repository.response

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val messageId: Int?) : Result<Nothing>()
}
