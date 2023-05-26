package com.example.data.repository

import com.example.data.model.profile.ProfileUpdateRequestBody
import com.example.data.source.UserRemoteDataSource
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {

    override suspend fun updateUserInfo(
        userId: Int,
        nickName: String,
        email: String,
        phoneNumber: String,
        password: String
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
}
