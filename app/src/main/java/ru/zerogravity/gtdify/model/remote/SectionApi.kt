package ru.zerogravity.gtdify.model.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.remote.request.Request

interface SectionApiScheme {
    @PUT("update")
    fun update(@Body request: Request.Authenticated<Section>): Deferred<Response<Section>>

    @GET("cards")
    fun getCards(
        @Query("token") token: String,
        @Query("sectionId") sectionId: Long
    ): Deferred<Response<List<Card>>>

    @POST("addCard")
    fun addCard(@Body request: Request.AuthenticatedWithId<Card>): Deferred<Response<Card>>
}

object SectionApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:8080/api/v1/section/")
        .build()
    val service: CardApiScheme = retrofit.create(CardApiScheme::class.java)
}