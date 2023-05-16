package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.exchangePost.ExchangePostListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangePostApi {

    @GET("/exchange/exchange-posts")
    suspend fun getAll(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Response<ResponseBody<ExchangePostListDto>>

}