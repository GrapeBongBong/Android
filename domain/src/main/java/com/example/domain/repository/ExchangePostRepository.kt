package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.exchange.ExchangePost
import kotlinx.coroutines.flow.Flow

interface ExchangePostRepository {
    fun getAll(): Flow<PagingData<ExchangePost>>
}