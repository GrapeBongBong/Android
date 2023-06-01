package com.example.android_bong.view.main.home

import com.example.android_bong.view.main.community.CommunityItemUiState
import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState

data class HomeUiState(
    val userMessage: String? = null,
    val exchangePosts: List<TalentExchangeItemUiState> = emptyList(),
    val communityPosts: List<CommunityItemUiState> = emptyList(),
    val isLoadingSuccess: Boolean = false,
    val isLoading: Boolean = false
)