package com.example.android_bong.view.main.talentexchange

import com.example.domain.model.exchange.AvailableTime
import com.example.domain.model.exchange.Image
import java.io.Serializable

data class TalentExchangeUiState(
    val userMessage: String? = null,
    val posts: List<TalentExchangeItemUiState> = emptyList(),
    val isLoadingSuccess: Boolean = false
)

data class TalentExchangeItemUiState(
    val availableTime: AvailableTime,
    val content: String,
    val date: String,
    val giveCate: String,
    val giveTalent: String,
    val images: List<Image>?,
    val pid: Int,
    val postType: String,
    val takeCate: String,
    val takeTalent: String,
    val title: String,
    val uid: Int,
    val writerId: String,
    val writerNick: String,
    val status: Boolean,
    val isMine: Boolean
) : Serializable