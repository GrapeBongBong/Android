package com.example.domain.repository

import com.example.domain.model.exchange.ExchangePost

interface ExchangePostRepository {

    suspend fun getAll(): Result<List<ExchangePost>>

    suspend fun deleteExchangePost(postId: Int): Result<String>

    suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        days: MutableList<String>,
        timeZone: String
    ): Result<String>

    suspend fun updateExchangePost(postId: Int): Result<String>

}