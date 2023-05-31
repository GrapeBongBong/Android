package com.example.android_bong.view.main.check.chatroom

data class CheckChatRoomUiState(
    val rooms: List<ChatRoomItemUiState> = emptyList(),
    val userMessage: String? = null,
    val isLoadingSuccess: Boolean = false,
    val isLoading: Boolean = false
)

data class ChatRoomItemUiState(
    val roomId: Int,
    val roomName: String,
    val date: String
)

