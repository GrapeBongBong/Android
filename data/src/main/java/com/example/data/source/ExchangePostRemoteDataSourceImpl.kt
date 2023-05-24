package com.example.data.source

import com.example.data.api.ExchangePostApi
import com.example.data.model.exchangePost.CreateExchangePostRequestBody
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.domain.model.exchange.AvailableTime
import retrofit2.Response
import javax.inject.Inject

class ExchangePostRemoteDataSourceImpl @Inject constructor(
    private val api: ExchangePostApi
) : ExchangePostRemoteDataSource {

    override suspend fun getAll(): Response<List<ExchangePostDto>> = api.getAll()
    override suspend fun deleteExchangePost(postId: Int): Response<Unit> =
        api.deleteExchangePost(postId = postId)

    override suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        availableTime: AvailableTime
    ): Response<Unit> = api.createExchangePost(
        CreateExchangePostRequestBody(
            title = title,
            content = content,
            giveCate = giveCate,
            takeCate = takeCate,
            giveTalent = giveTalent,
            takeTalent = takeTalent,
            availableTime = availableTime
        )
    )

}