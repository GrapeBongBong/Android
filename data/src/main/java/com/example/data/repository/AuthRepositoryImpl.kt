package com.example.data.repository

import com.example.data.extension.getDataOrThrowMessage
import com.example.data.mapper.toEntity
import com.example.data.model.auth.AuthLocalData
import com.example.data.model.auth.LoginRequestBody
import com.example.data.model.auth.SignUpRequestBody
import com.example.data.source.AuthLocalDataSource
import com.example.data.source.AuthRemoteDataSource
import com.example.domain.model.user.User
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    private val isReady = MutableStateFlow(false)

    private val currentUserState: MutableStateFlow<User?> = MutableStateFlow(null)

    override suspend fun getUserDetail(): StateFlow<User?> {
        return currentUserState
    }

    override suspend fun login(id: String, password: String): Result<Unit> {
        return runCatching {
            val response = remoteDataSource.login(
                LoginRequestBody(id = id, password = password)
            )
            val loginResponseBody = response.getDataOrThrowMessage()

            currentUserState.value = loginResponseBody.user.toEntity()
            localDataSource.setData(
                authLocalData = AuthLocalData(
                    sessionToken = loginResponseBody.token
                )
            )
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