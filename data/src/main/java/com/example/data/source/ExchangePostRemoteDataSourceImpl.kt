package com.example.data.source

import com.example.data.api.ExchangePostApi
import com.example.data.model.exchangePost.ExchangePostDto
import retrofit2.Response
import javax.inject.Inject

class ExchangePostRemoteDataSourceImpl @Inject constructor(
    private val api: ExchangePostApi
) : ExchangePostRemoteDataSource {

    override suspend fun getAll(): Response<List<ExchangePostDto>> = api.getAll()

}