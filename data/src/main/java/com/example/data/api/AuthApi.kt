package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.auth.LoginDto
import com.example.data.model.auth.LoginRequestBody
import com.example.data.model.auth.SignUpRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): Response<ResponseBody<LoginDto>>

    @POST("auth/join")
    suspend fun signUp(
        @Body signUpRequestBody: SignUpRequestBody
    ): Response<ResponseBody<Unit>>

}