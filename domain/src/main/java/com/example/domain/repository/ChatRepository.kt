package com.example.domain.repository

import com.example.domain.model.chat.ChatRoom
import com.example.domain.model.chat.PostChatRoom

interface ChatRepository {

    suspend fun createChatRoom(
        exchangePostId: Int,
        applicantId: String
    ): Result<String>

    suspend fun getAllMyChatRoom(): Result<List<ChatRoom>>

    suspend fun getAllThisPostChatRoom(postId: Int): Result<List<PostChatRoom>>

    suspend fun successMatching(postId: Int): Result<String>

    suspend fun applyScore(postId: Int, score: Int): Result<String>

}