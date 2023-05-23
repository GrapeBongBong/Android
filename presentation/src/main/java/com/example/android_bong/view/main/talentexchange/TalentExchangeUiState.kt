package com.example.android_bong.view.main.talentexchange

data class TalentExchangeUiState(
    val userMessage: String? = null,
    val posts: List<TalentExchangeItemUiState> = emptyList()
)