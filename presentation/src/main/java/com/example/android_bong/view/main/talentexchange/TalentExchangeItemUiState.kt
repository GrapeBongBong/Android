package com.example.android_bong.view.main.talentexchange

import com.example.domain.model.exchange.AvailableTime
import java.io.Serializable

data class TalentExchangeItemUiState(
    val availableTime: AvailableTime,
    val content: String,
    val date: String,
    val giveCate: String,
    val giveTalent: String,
    val images: List<Any>,
    val pid: Int,
    val postType: String,
    val takeCate: String,
    val takeTalent: String,
    val title: String,
    val uid: Int,
    val writerId: String,
    val writerNick: String
) : Serializable