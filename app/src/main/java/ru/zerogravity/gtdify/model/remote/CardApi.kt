package ru.zerogravity.gtdify.model.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.remote.request.Request

interface CardApiScheme {
    @PUT("update")
    fun update(@Body request: Request.Authenticated<Card>): Deferred<Response<Card>>

    @DELETE("delete")
    fun delete(@Body request: Request.Authenticated<Long>): Deferred<Response<Any>>
}

object CardApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:8080/api/v1/card/")
        .build()
    private val service: CardApiScheme = retrofit.create(CardApiScheme::class.java)
        get() = field
}