package ru.zerogravity.gtdify.model.remote.request

sealed class Request<out T : Any> {
    data class Authenticated<out T : Any>(val token: String, val data: T) : Request<T>()
    data class AuthenticatedWithId<out T : Any>(val token: String, val id: Long, val data: T)
}
