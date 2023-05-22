package com.example.data.repository

import com.example.data.model.profile.ProfileUpdateRequestBody
import com.example.data.source.UserRemoteDataSource
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun updateUserInfo(
        userId: Int,
        nickName: String,
        address: String
    ): Result<Unit> {
        return try {
            val response = remoteDataSource.updateUserInfo(
                userId = userId,
                ProfileUpdateRequestBody(
                    nickName = nickName,
                    address = address
                )
            )
            if (response.code() == 200) {
                Result.success(Unit)
            } else {
                throw Exception("정보 수정에 실패하였습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}