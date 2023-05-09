package com.example.domain.repository

interface AuthRepository {

    suspend fun login(id: String, password: String): Result<Unit>

    suspend fun signUp(
        id: String,
        password: String,
        name: String,
        nickName: String,
        birth: String,
        email: String,
        phoneNum: String,
        address: String,
        gender: String
    ): Result<Unit>

}