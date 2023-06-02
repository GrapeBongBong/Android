package com.example.domain.model.chat

data class ChatMessage(
    val roomId: Int,
    val senderId: String,
    val message: String
)