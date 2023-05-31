package com.example.data.mapper

import com.example.data.model.chat.ChatRoomDto
import com.example.domain.model.chat.ChatRoom

fun ChatRoomDto.toEntity() = ChatRoom(
    roomId = roomId,
    roomName = roomName,
    date = date
)