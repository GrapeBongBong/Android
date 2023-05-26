package com.example.domain.model.community

import com.example.domain.model.exchange.Image

data class CommunityPost(
    val content: String,
    val date: String,
    val image: List<Image>? = emptyList(),
    val pid: Int,
    val postType: String,
    val title: String,
    val uid: Int,
    val writerId: String,
    val writerNick: String,
)