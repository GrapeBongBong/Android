package com.example.data.mapper

import com.example.data.model.chat.PostChatRoomDto
import com.example.domain.model.chat.PostChatRoom

fun PostChatRoomDto.toEntity() = PostChatRoom(
    postWriterUID = postWriterUID,
    pid = pid,
    roomId = roomId,
    roomName = roomName,
    date = date
)