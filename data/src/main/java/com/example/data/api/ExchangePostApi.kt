package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.exchangePost.ExchangePostListDto
import retrofit2.Response
import retrofit2.http.GET

interface ExchangePostApi {

    @GET("/exchange/posts")
    suspend fun getAll(): Response<ExchangePostListDto>

    suspend fun deleteExchangePost(

    ): Response<Unit>

    suspend fun updateExchangePost(

    ): Response<Unit>

    suspend fun createExchangePost(

    ): Response<Unit>

}