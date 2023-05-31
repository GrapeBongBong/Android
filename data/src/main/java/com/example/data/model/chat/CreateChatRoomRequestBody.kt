package com.example.data.model.chat

data class CreateChatRoomRequestBody(
    val exchangePostId: Int,
    val applicantId: String
)