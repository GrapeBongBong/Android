package com.example.domain.repository

interface UserRepository {

    suspend fun updateUserInfo(
        userId: Int,
        nickName: String,
        email: String,
        phoneNumber: String,
        password: String
    ): Result<String>

}