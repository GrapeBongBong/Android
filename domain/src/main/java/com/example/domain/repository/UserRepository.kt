package com.example.domain.repository

interface UserRepository {

    suspend fun updateUserInfo(userId: Int, nickName: String, address: String): Result<String>

}