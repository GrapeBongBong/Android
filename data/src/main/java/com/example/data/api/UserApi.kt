package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.data.model.profile.ProfileUpdateRequestBody
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @PUT("user/profile/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: Int,
        @Body profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<ResponseBody>

    @Multipart
    @PUT("/user/profile/image")
    suspend fun updateUserProfileImage(
        @Part profileImage: MultipartBody.Part?
    ): Response<ResponseBody>

    @GET("/profile/anonymous")
    suspend fun myCommunityPost(): Response<List<CommunityDto>>

    @GET("/profile/exchange")
    suspend fun myExchangePost(): Response<List<ExchangePostDto>>

}