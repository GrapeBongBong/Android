package com.example.data.source

import com.example.data.api.UserApi
import com.example.data.model.profile.ProfileUpdateRequestBody
import com.example.data.model.user.ProfileUpdateResponseBody
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val api: UserApi
) : UserRemoteDataSource {
    override suspend fun updateUserInfo(
        userId: Int,
        profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<ProfileUpdateResponseBody> = api.updateUserInfo(
        userId = userId,
        profileUpdateRequestBody = profileUpdateRequestBody
    )
}