package com.example.android_bong.view.main.talentexchange.detail

import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState

data class TalentExchangeDetailUiState(
    val userMessage: String? = null,
    val postId: Int? = null,
    val postDetail: TalentExchangeItemUiState? = null,
    val postDeletingSuccess: Boolean = false
)
