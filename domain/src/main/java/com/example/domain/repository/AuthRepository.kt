package com.example.domain.repository

import com.example.domain.model.user.User
import kotlinx.coroutines.flow.StateFlow

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

    suspend fun getUserDetail(): StateFlow<User?>

    fun syncCurrentUser(
        nickName: String, address: String
    )

}