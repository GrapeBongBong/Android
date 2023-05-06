package com.example.data.repository

import com.example.data.extension.getDataOrThrowMessage
import com.example.data.model.auth.LoginRequestBody
import com.example.data.source.AuthRemoteDataSource
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(id: String, password: String): Result<Unit> {
        return runCatching {
            val response = remoteDataSource.login(
                LoginRequestBody(id = id, password = password)
            )
            response.getDataOrThrowMessage()
        }
    }

    override suspend fun signUp(): Result<Unit> {
        TODO("Not yet implemented")
    }

}