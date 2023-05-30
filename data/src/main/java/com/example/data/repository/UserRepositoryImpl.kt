package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.model.profile.ProfileUpdateRequestBody
import com.example.data.source.UserRemoteDataSource
import com.example.domain.model.community.CommunityPost
import com.example.domain.model.exchange.ExchangePost
import com.example.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override suspend fun updateUserInfo(
        userId: Int,
        nickName: String,
        email: String,
        phoneNumber: String,
        password: String,
        profileImage: File?
    ): Result<String> {
        return try {
            val response = remoteDataSource.updateUserInfo(
                userId = userId,
                profileUpdateRequestBody = ProfileUpdateRequestBody(
                    nickName = nickName,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password
                )
            )
            val imageResponse = remoteDataSource.updateUserProfileImage(
                profileImage = profileImage
            )
            val imageResponseBody = imageResponse.body()
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(responseBody.message)
            } else {
                throw Exception(response.body()!!.message)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserProfileImage(
        profileImage: File?
    ): Result<String> {
        return try {
            val response = remoteDataSource.updateUserProfileImage(
                profileImage = profileImage
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(responseBody.message)
            } else {
                throw Exception(response.body()!!.message)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun myCommunityPost(): Result<List<CommunityPost>> {
        return try {
            val response = remoteDataSource.myCommunityPost()
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

    override suspend fun myExchangePost(): Result<List<ExchangePost>> {
        return try {
            val response = remoteDataSource.myExchangePost()
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

}
