package com.example.android_bong.view.chat

data class ChattingRoomUiSate(
    val rooms: List<PostChatRoomItemUiState> = emptyList(),
    val userMessage: String? = null,
    val postId: Int? = null,
    val isLoadingSuccess: Boolean = false,
    val isLoading: Boolean = false
)

data class PostChatRoomItemUiState(
    val postWriterUID: Int,
    val pid: Int,
    val roomId: Int,
    val roomName: String,
    val date: String
)
