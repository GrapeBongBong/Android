package com.example.data.api

import com.example.data.model.exchangePost.ExchangePostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface ExchangePostApi {

    @GET("/exchange/posts")
    suspend fun getAll(): Response<List<ExchangePostDto>>

    @HTTP(method = "DELETE", path = "/exchange/delete/{postId}", hasBody = true)
    suspend fun deleteExchangePost(

    ): Response<Unit>

    @PUT("/exchange/post/{postId}")
    suspend fun updateExchangePost(

    ): Response<Unit>

    @POST("/exchange/post")
    suspend fun createExchangePost(

    ): Response<Unit>

}