package com.example.android_bong.view.main.check.exchange

import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState

data class CheckExchangePostUiState(
    val userMessage: String? = null,
    val posts: List<TalentExchangeItemUiState> = emptyList(),
    val isLoadingSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val userId: Int? = null
)