package com.example.android_bong.view.chat.chatting

import com.example.domain.model.chat.ChatMessage

data class ChattingUiState(
    val userMessage: String? = null,
    val postId: Int? = null,
    val roomId: Int? = null,
    val roomTitle: String? = null,
    val chatting: MutableList<ChatMessage> = mutableListOf(),
    val myChatMessage: String = "",
    val score: Int? = null,
    val senderId: String = "",
    val isLoading: Boolean = false
)
