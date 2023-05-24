package com.example.data.source

import com.example.data.model.exchangePost.ExchangePostDto
import com.example.domain.model.exchange.AvailableTime
import retrofit2.Response

interface ExchangePostRemoteDataSource {

    suspend fun getAll(): Response<List<ExchangePostDto>>

    suspend fun deleteExchangePost(postId: Int): Response<Unit>

    suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        availableTime: AvailableTime
    ): Response<Unit>

}