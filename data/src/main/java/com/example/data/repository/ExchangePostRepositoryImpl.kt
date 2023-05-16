package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.api.ExchangePostApi
import com.example.data.paging.ExchangePagingSource
import com.example.data.paging.ExchangePagingSource.Companion.PAGE_SIZE
import com.example.domain.model.exchange.ExchangePost
import com.example.domain.repository.ExchangePostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExchangePostRepositoryImpl @Inject constructor(
    private val api: ExchangePostApi
) : ExchangePostRepository {

    override fun getAll(): Flow<PagingData<ExchangePost>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { ExchangePagingSource(api = api) }
        ).flow
    }
}