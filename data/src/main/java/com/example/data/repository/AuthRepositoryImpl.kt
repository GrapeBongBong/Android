package com.example.data.repository

import androidx.core.net.toUri
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
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    private val currentUserState: MutableStateFlow<User?> = MutableStateFlow(null)

    override fun getUserDetail(): StateFlow<User?> {
        return currentUserState
    }

    override suspend fun login(id: String, password: String): Result<Unit> {
        return try {
            val response = remoteDataSource.login(
                LoginRequestBody(id = id, password = password)
            )
            val loginResponseBody = response.body()
            if (loginResponseBody != null && response.code() == 200) {
                currentUserState.value = loginResponseBody.user.toEntity()
                localDataSource.setData(
                    authLocalData = AuthLocalData(
                        sessionToken = loginResponseBody.token
                    )
                )
                Result.success(Unit)
            } else if (response.code() == 401) {
                throw Exception("비밀번호가 틀렸습니다.")
            } else if (response.code() == 404) {
                throw Exception("가입되어 있지 않은 사용자입니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
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
    ): Result<String> {
        return try {
            val result = remoteDataSource.signUp(
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
            val responseBody = result.body()
            if (responseBody != null && result.code() == 200) {
                val message = responseBody.message
                Result.success(message)
            } else {
                val message = responseBody!!.message
                throw Exception(message)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun syncCurrentUser(
        nickName: String,
        email: String,
        phoneNumber: String,
        password: String,
        profileImage: File?
    ) {
        val currentUserStateValue = currentUserState.value
        if (currentUserStateValue != null) {
            currentUserState.value = currentUserStateValue.copy(
                nickName = nickName,
                email = email,
                phone_num = phoneNumber,
                password = password,
                profile_img = profileImage?.toUri().toString()
            )
        }
    }

}