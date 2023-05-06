package com.example.data.source

import com.example.data.api.AuthApi
import com.example.data.model.ResponseBody
import com.example.data.model.auth.LoginDto
import com.example.data.model.auth.LoginRequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val api: AuthApi
) : AuthRemoteDataSource {
    override suspend fun login(loginRequestBody: LoginRequestBody): Response<ResponseBody<LoginDto>> =
        api.login(loginRequestBody = loginRequestBody)
}