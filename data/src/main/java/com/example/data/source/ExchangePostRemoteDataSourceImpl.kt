package com.example.data.source

import com.example.data.api.ExchangePostApi
import com.example.data.model.ResponseBody
import com.example.data.model.exchangePost.CreateExchangePostRequestBody
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.domain.model.exchange.AvailableTime
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class ExchangePostRemoteDataSourceImpl @Inject constructor(
    private val api: ExchangePostApi
) : ExchangePostRemoteDataSource {

    override suspend fun getAll(): Response<List<ExchangePostDto>> = api.getAll()

    override suspend fun deleteExchangePost(postId: Int): Response<ResponseBody> =
        api.deleteExchangePost(postId = postId)

    override suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        availableTime: AvailableTime
    ): Response<ResponseBody> {

        val data = CreateExchangePostRequestBody(
            title = title,
            content = content,
            giveCate = giveCate,
            takeCate = takeCate,
            giveTalent = giveTalent,
            takeTalent = takeTalent,
            availableTime = availableTime
        )

        // Gson을 사용하여 객체를 JSON 형식의 문자열로 변환
        val gson = Gson()
        val json = gson.toJson(data)

        val jsonRequestBody: RequestBody =
            json.toRequestBody("application/json".toMediaTypeOrNull())

        return api.createExchangePost(
            jsonRequestBody
        )
    }

    override suspend fun updateExchangePost(
        postId: Int,
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        availableTime: AvailableTime
    ): Response<ResponseBody> {
        return api.updateExchangePost(
            postId = postId,
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

    override suspend fun clickLikeExchange(postId: Int): Response<ResponseBody> =
        api.clickLikeExchange(postId = postId)

    override suspend fun clickUnLikeExchange(postId: Int): Response<ResponseBody> =
        api.clickUnLikeExchange(postId = postId)


}