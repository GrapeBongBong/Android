package com.example.android_bong.view.main.community

import com.example.domain.model.exchange.Image

data class CommunityUiState(
    val userMessage: String? = null,
    val posts: List<CommunityItemUiState> = emptyList(),
    val isLoadingSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val userId: Int? = null
)

data class CommunityItemUiState(
    val content: String,
    val date: String,
    val images: List<Image>? = emptyList(),
    val pid: Int,
    val postType: String,
    val title: String,
    val uid: Int,
    val writerImageURL: String? = null,
    val writerId: String,
    val writerNick: String,
    val isMine: Boolean,
    val likeCount: Int,
    val liked: Boolean
)