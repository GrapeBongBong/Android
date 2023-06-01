package com.example.android_bong.mapper

import com.example.android_bong.view.chat.chatting.ChattingItemUiState
import com.example.domain.model.chat.ChatMessage

fun ChatMessage.toUiState(uid: String) = ChattingItemUiState(
    roomId = roomId,
    senderId = senderId,
    message = message,
    isMine = (senderId == uid),
    messageId = messageId
)