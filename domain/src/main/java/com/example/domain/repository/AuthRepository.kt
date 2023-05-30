package com.example.domain.repository

import com.example.domain.model.user.User
import kotlinx.coroutines.flow.StateFlow
import java.io.File

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
    ): Result<String>

    fun getUserDetail(): StateFlow<User?>

    fun syncCurrentUser(
        nickName: String,
        email: String,
        phoneNumber: String,
        password: String,
        profileImage: File?
    )

}