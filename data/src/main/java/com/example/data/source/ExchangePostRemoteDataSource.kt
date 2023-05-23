package com.example.data.source

import com.example.data.model.exchangePost.ExchangePostDto
import retrofit2.Response

interface ExchangePostRemoteDataSource {

    suspend fun getAll(): Response<List<ExchangePostDto>>

}