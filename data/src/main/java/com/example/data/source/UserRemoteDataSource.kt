package com.example.data.source

import com.example.data.model.profile.ProfileUpdateRequestBody
import com.example.data.model.user.ProfileUpdateResponseBody
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun updateUserInfo(
        userId: Int,
        profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<ProfileUpdateResponseBody>
}