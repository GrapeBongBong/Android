package com.example.android_bong.view.chat.chatting

data class ChattingUiState(
    val userMessage: String? = null,
    val postId: Int? = null,
    val roomId: Int? = null,
    val roomTitle: String? = null,
    val chatting: List<ChattingItemUiState> = emptyList(),
    val myChatMessage: String = "",
    val score: Int? = null,
    val senderId: String = ""
)

data class ChattingItemUiState(
    val roomId: Int,
    val senderId: String,
    val message: String,
    val isMine: Boolean,
    val messageId: Int
)

