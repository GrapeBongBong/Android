package com.example.android_bong.view.main.check.community

import com.example.android_bong.view.main.community.CommunityItemUiState

data class CheckCommunityPostUiState(
    val userMessage: String? = null,
    val posts: List<CommunityItemUiState> = emptyList(),
    val isLoadingSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val userId: Int? = null
)