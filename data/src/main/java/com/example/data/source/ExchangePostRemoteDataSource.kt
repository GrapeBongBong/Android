package com.example.data.source

import com.example.data.model.ResponseBody
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.domain.model.exchange.AvailableTime
import retrofit2.Response
import java.io.File

interface ExchangePostRemoteDataSource {

    suspend fun getAll(): Response<List<ExchangePostDto>>

    suspend fun deleteExchangePost(postId: Int): Response<ResponseBody>

    suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        availableTime: AvailableTime,
        images: List<File?>
    ): Response<ResponseBody>

    suspend fun updateExchangePost(
        postId: Int,
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        availableTime: AvailableTime
    ): Response<ResponseBody>

    suspend fun clickLikeExchange(postId: Int): Response<ResponseBody>

    suspend fun clickUnLikeExchange(postId: Int): Response<ResponseBody>

}