package com.example.android_bong.view.chat.chatting

data class ChattingUiState(
    val userMessage: String? = null,
    val postId: Int? = null,
    val roomId: Int? = null,
    val roomTitle: String? = null,
    val myChatMessage: String? = null,
    val score: Int? = null
)