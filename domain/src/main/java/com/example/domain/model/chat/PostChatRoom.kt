package com.example.domain.model.chat

data class PostChatRoom(
    val postWriterUID: Int,
    val pid: Int,
    val roomId: Int,
    val roomName: String,
    val date: String
)