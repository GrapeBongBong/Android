package com.example.domain.model.chat

data class ChatMessage(
    val roomId: Int,
    val senderId: String,
    val message: String,
    val messageId: String // 메시지 아이디 추가
)