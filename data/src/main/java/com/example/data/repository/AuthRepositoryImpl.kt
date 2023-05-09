package com.example.data.repository

import com.example.data.extension.getDataOrThrowMessage
import com.example.data.model.auth.LoginRequestBody
import com.example.data.model.auth.SignUpRequestBody
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

    override suspend fun signUp(
        id: String,
        password: String,
        name: String,
        nickName: String,
        birth: String,
        email: String,
        phoneNum: String,
        address: String,
        gender: String
    ): Result<Unit> {
        return runCatching {
            val response = remoteDataSource.signUp(
                SignUpRequestBody(
                    id = id,
                    password = password,
                    name = name,
                    nickName = nickName,
                    birth = birth,
                    email = email,
                    phoneNum = phoneNum,
                    address = address,
                    gender = gender
                )
            )
            response.getDataOrThrowMessage()
        }
    }

}