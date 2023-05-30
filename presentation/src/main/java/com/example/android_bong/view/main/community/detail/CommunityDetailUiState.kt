package com.example.android_bong.view.main.community.detail

import com.example.android_bong.view.main.community.CommunityItemUiState

data class CommunityDetailUiState(
    val userMessage: String? = null,
    val postId: Int? = null,
    val postDetail: CommunityItemUiState? = null,
    val postDeletingSuccess: Boolean = false
)