package com.example.domain.repository

import com.example.domain.model.exchange.ExchangePost

interface ExchangePostRepository {
    suspend fun getAll(): Result<List<ExchangePost>>
}