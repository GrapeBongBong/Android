package com.example.data.source

import com.example.data.api.ExchangePostApi
import com.example.data.model.ResponseBody
import com.example.data.model.exchangePost.CreateExchangePostRequestBody
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.data.model.exchangePost.GetAllWithFilterRequestBody
import com.example.domain.model.exchange.AvailableTime
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ExchangePostRemoteDataSourceImpl @Inject constructor(
    private val api: ExchangePostApi
) : ExchangePostRemoteDataSource {

    companion object {
        private const val IMAGE_KEY = "images"
    }

    override suspend fun getAll(): Response<List<ExchangePostDto>> = api.getAll()

    override suspend fun getAllWithFilter(
        takeCate: String,
        giveCate: String
    ): Response<List<ExchangePostDto>> {
        return api.getAllWithFilter(
            GetAllWithFilterRequestBody(
                giveCate = giveCate,
                takeCate = takeCate
            )
        )
    }

    override suspend fun deleteExchangePost(postId: Int): Response<ResponseBody> =
        api.deleteExchangePost(postId = postId)

    override suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        availableTime: AvailableTime,
        images: List<File?>
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

        val imagesPart = images.map { image ->
            if (image != null) {
                MultipartBody.Part.createFormData(
                    IMAGE_KEY,
                    image.name,
                    image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
            } else {
                MultipartBody.Part.createFormData(IMAGE_KEY, "")
            }
        }

        val gson = Gson()
        val json = gson.toJson(data)

        val jsonRequestBody: RequestBody =
            json.toRequestBody("application/json".toMediaTypeOrNull())

        return api.createExchangePost(
            jsonRequestBody,
            imagesPart
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

    override suspend fun applyScore(postId: Int, score: Int): Response<ResponseBody> =
        api.applyScore(
            postId = postId, score = score
        )

    override suspend fun getPopularExchangePost(): Response<List<ExchangePostDto>> =
        api.getPopularExchangePost()

}