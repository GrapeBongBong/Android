package com.example.data.model.community

import com.example.data.model.exchangePost.ImageDto

data class CommunityDto(
    val content: String,
    val date: String,
    val image: List<ImageDto>? = emptyList(),
    val pid: Int,
    val postType: String,
    val title: String,
    val uid: Int,
    val writerId: String,
    val writerNick: String,
)