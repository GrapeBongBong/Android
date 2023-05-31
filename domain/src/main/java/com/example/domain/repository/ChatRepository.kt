package com.example.domain.repository

import com.example.domain.model.chat.ChatRoom

interface ChatRepository {

    suspend fun createChatRoom(
        exchangePostId: Int,
        applicantId: String
    ): Result<String>

    suspend fun getAllMyChatRoom(): Result<List<ChatRoom>>
}