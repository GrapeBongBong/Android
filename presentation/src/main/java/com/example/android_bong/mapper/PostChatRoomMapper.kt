package com.example.android_bong.mapper

import com.example.android_bong.view.chat.PostChatRoomItemUiState
import com.example.domain.model.chat.PostChatRoom

fun PostChatRoom.toUiState() = PostChatRoomItemUiState(
    roomId = roomId,
    roomName = roomName,
    date = date,
    postWriterUID = postWriterUID,
    pid = pid
)