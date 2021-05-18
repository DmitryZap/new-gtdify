package ru.zerogravity.gtdify.model.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import ru.zerogravity.gtdify.model.Card
import ru.zerogravity.gtdify.model.Project
import ru.zerogravity.gtdify.model.Section
import ru.zerogravity.gtdify.model.remote.request.Request

interface ProjectApiScheme {
    @PUT("update")
    fun update(@Body request: Request.Authenticated<Project>): Deferred<Response<Project>>

    @GET("projects")
    fun getProjects(@Query("token") token: String): Deferred<Response<List<Project>>>
}

object ProjectApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:8080/api/v1/project/")
        .build()
    val service: CardApiScheme = retrofit.create(CardApiScheme::class.java)
}