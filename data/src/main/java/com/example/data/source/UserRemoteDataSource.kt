package com.example.data.source

import com.example.data.model.ResponseBody
import com.example.data.model.chat.ChatRoomDto
import com.example.data.model.community.CommunityDto
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.data.model.profile.ProfileUpdateRequestBody
import retrofit2.Response
import java.io.File

interface UserRemoteDataSource {
    suspend fun updateUserInfo(
        userId: Int,
        profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<ResponseBody>

    suspend fun updateUserProfileImage(
        profileImage: File?
    ): Response<ResponseBody>

    suspend fun myCommunityPost(): Response<List<CommunityDto>>

    suspend fun myExchangePost(): Response<List<ExchangePostDto>>

    suspend fun myChatRoom(): Response<List<ChatRoomDto>>
}