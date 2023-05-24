package com.example.domain.model.exchange

data class ExchangePost(
    val availableTime: AvailableTime,
    val content: String,
    val date: String,
    val giveCate: String,
    val giveTalent: String,
    val images: List<Image>? = emptyList(),
    val pid: Int,
    val postType: String,
    val takeCate: String,
    val takeTalent: String,
    val title: String,
    val uid: Int,
    val writerId: String,
    val writerNick: String,
    val status: Boolean
)