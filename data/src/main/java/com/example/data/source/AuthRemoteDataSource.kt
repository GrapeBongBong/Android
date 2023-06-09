package com.example.data.source

import com.example.data.model.ResponseBody
import com.example.data.model.auth.LoginRequestBody
import com.example.data.model.auth.LoginResponseBody
import com.example.data.model.auth.SignUpRequestBody
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponseBody>
    suspend fun signUp(signUpRequestBody: SignUpRequestBody): Response<ResponseBody>
}