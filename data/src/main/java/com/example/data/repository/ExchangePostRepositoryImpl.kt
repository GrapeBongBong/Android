package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.source.ExchangePostRemoteDataSource
import com.example.domain.model.exchange.AvailableTime
import com.example.domain.model.exchange.ExchangePost
import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class ExchangePostRepositoryImpl @Inject constructor(
    private val exchangePostRemoteDataSource: ExchangePostRemoteDataSource
) : ExchangePostRepository {

    override suspend fun getAll(): Result<List<ExchangePost>> {
        return try {
            val response = exchangePostRemoteDataSource.getAll()
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                val data = responseBody.map {
                    it.toEntity()
                }
                Result.success(data)
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteExchangePost(postId: Int): Result<Unit> {
        return try {
            val response = exchangePostRemoteDataSource.deleteExchangePost(postId)
            if (response.isSuccessful && response.code() == 200) {
                Result.success(Unit)
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createExchangePost(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        days: MutableList<String>,
        timeZone: String
    ): Result<Unit> {
        return try {
            val response = exchangePostRemoteDataSource.createExchangePost(
                title = title,
                content = content,
                giveCate = giveCate,
                takeCate = takeCate,
                giveTalent = giveTalent,
                takeTalent = takeTalent,
                availableTime = AvailableTime(
                    days = days,
                    timezone = timeZone
                )
            )
            if (response.isSuccessful && response.code() == 200) {
                Result.success(Unit)
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else if (response.code() == 403) {
                throw Exception("필수 정보가 없습니다.")
            } else if (response.code() == 404) {
                throw Exception("회원이 아닙니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생합니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateExchangePost(postId: Int): Result<Unit> {
        return Result.success(Unit)
    }
}