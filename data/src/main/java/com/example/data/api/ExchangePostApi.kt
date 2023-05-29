package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.exchangePost.CreateExchangePostRequestBody
import com.example.data.model.exchangePost.ExchangePostDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ExchangePostApi {

    @GET("/exchange/posts")
    suspend fun getAll(): Response<List<ExchangePostDto>>

    @HTTP(method = "DELETE", path = "/exchange/delete/{postId}", hasBody = false)
    suspend fun deleteExchangePost(
        @Path("postId") postId: Int
    ): Response<ResponseBody>

    @PUT("/exchange/post/{postId}")
    suspend fun updateExchangePost(
        @Path("postId") postId: Int,
        @Body createExchangePostRequestBody: CreateExchangePostRequestBody
    ): Response<ResponseBody>

    @Multipart
    @POST("/exchange/post")
    suspend fun createExchangePost(
        @Part("exchangePostDTO") requestBody: RequestBody
    ): Response<ResponseBody>

}