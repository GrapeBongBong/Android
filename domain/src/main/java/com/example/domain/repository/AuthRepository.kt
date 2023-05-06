package com.example.domain.repository

interface AuthRepository {

    suspend fun login(id: String, password: String): Result<Unit>

    suspend fun signUp(): Result<Unit>

}