package com.example.android_bong.view.main.talentexchange

import androidx.paging.PagingData

data class TalentExchangeUiState(
    val userMessage: String? = null,
    val pagingData: PagingData<TalentExchangeItemUiState> = PagingData.empty()
)