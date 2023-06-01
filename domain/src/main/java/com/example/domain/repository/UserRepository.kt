package com.example.domain.repository

import com.example.domain.model.community.CommunityPost
import com.example.domain.model.exchange.ExchangePost
import java.io.File

interface UserRepository {

    suspend fun updateUserInfo(
        userId: Int,
        nickName: String,
        email: String,
        phoneNumber: String,
        password: String,
        profileImage: File?
    ): Result<String>

    suspend fun updateUserProfileImage(
        profileImage: File?
    ): Result<String>

    suspend fun myCommunityPost(): Result<List<CommunityPost>>

    suspend fun myExchangePost(): Result<List<ExchangePost>>

    suspend fun myCompletedMatches(): Result<List<ExchangePost>>

}