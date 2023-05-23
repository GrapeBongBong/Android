package com.example.data.source

import com.example.data.api.AuthApi
import com.example.data.model.ResponseBody
import com.example.data.model.auth.LoginResponseBody
import com.example.data.model.auth.LoginRequestBody
import com.example.data.model.auth.SignUpRequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val api: AuthApi
) : AuthRemoteDataSource {
    override suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponseBody> =
        api.login(loginRequestBody = loginRequestBody)

    override suspend fun signUp(signUpRequestBody: SignUpRequestBody): Response<ResponseBody<Unit>> =
        api.signUp(signUpRequestBody = signUpRequestBody)
}