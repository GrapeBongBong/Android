package com.example.data.api

import com.example.data.model.profile.ProfileUpdateRequestBody
import com.example.data.model.user.ProfileUpdateResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @PUT("user/profile/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: Int,
        @Body profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<ProfileUpdateResponseBody>
}