package com.example.android_bong.mapper

import com.example.android_bong.view.main.check.chatroom.ChatRoomItemUiState
import com.example.domain.model.chat.ChatRoom

fun ChatRoom.toUiState() = ChatRoomItemUiState(
    roomId = roomId,
    roomName = roomName,
    date = date
)