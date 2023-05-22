package com.example.data.model.exchangePost

data class ExchangePostDto(
    val availableTimeDto: AvailableTimeDto,
    val content: String,
    val date: String,
    val giveCate: String,
    val giveTalent: String,
    val imageDtos: List<ImageDto>,
    val pid: Int,
    val postType: String,
    val takeCate: String,
    val takeTalent: String,
    val title: String,
    val uid: Int,
    val writerId: String,
    val writerNick: String
)