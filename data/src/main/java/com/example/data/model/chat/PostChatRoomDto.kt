package com.example.data.model.chat

data class PostChatRoomDto(
    val postWriterUID: Int,
    val pid: Int,
    val roomId: Int,
    val roomName: String,
    val date: String
)
