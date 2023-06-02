package com.example.domain.repository

import com.example.domain.model.exchange.ExchangePost
import java.io.File

interface ExchangePostRepository {

    suspend fun getAll(): Result<List<ExchangePost>>

    suspend fun getAllWithFilter(
        takeCate: String,
        giveCate: String
    ): Result<List<ExchangePost>>

    suspend fun deleteExchangePost(postId: Int): Result<String>

    suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        days: List<String>,
        timeZone: String,
        images: List<File?>
    ): Result<String>

    suspend fun updateExchangePost(
        postId: Int,
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        days: MutableList<String>,
        timeZone: String
    ): Result<String>

    suspend fun clickLike(
        postId: Int
    ): Result<String>

    suspend fun clickUnLike(
        postId: Int
    ): Result<String>

    suspend fun getPopularExchangePost(): Result<List<ExchangePost>>


}