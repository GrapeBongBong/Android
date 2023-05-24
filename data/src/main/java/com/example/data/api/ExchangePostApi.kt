package com.example.data.api

import com.example.data.model.exchangePost.CreateExchangePostRequestBody
import com.example.data.model.exchangePost.ExchangePostDto
import retrofit2.Response
import retrofit2.http.*

interface ExchangePostApi {

    @GET("/exchange/posts")
    suspend fun getAll(): Response<List<ExchangePostDto>>

    @HTTP(method = "DELETE", path = "/exchange/delete/{postId}", hasBody = false)
    suspend fun deleteExchangePost(
        @Path("postId") postId: Int
    ): Response<Unit>

    @PUT("/exchange/post/{postId}")
    suspend fun updateExchangePost(
        @Path("postId") postId: Int
    ): Response<Unit>

    @POST("/exchange/post")
    suspend fun createExchangePost(
        @Body createExchangePostRequestBody: CreateExchangePostRequestBody
    ): Response<Unit>

}