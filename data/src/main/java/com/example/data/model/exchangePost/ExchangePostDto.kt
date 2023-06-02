package com.example.data.model.exchangePost

data class ExchangePostDto(
    val availableTime: AvailableTimeDto,
    val content: String,
    val date: String,
    val giveCate: String,
    val giveTalent: String,
    val images: List<ImageDto>? = emptyList(),
    val pid: Int,
    val postType: String,
    val takeCate: String,
    val takeTalent: String,
    val title: String,
    val uid: Int,
    val writerImageURL: String? = null,
    val writerId: String,
    val writerNick: String,
    val status: Boolean,
    val liked: Boolean,
    val likeCount: Int,
    val temperature: Double
)

data class AvailableTimeDto(
    val days: List<String> = emptyList(),
    val timezone: String? = null
)

data class ImageDto(
    val fileUrl: String,
    val id: Int
)