package com.example.data.source

import com.example.data.api.UserApi
import com.example.data.model.ResponseBody
import com.example.data.model.profile.ProfileUpdateRequestBody
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val api: UserApi
) : UserRemoteDataSource {
    override suspend fun updateUserInfo(
        userId: Int,
        profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<ResponseBody> = api.updateUserInfo(
        userId = userId,
        profileUpdateRequestBody = profileUpdateRequestBody
    )
}