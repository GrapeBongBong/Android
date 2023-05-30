package com.example.domain.model.community

import com.example.domain.model.exchange.Image

data class CommunityPost(
    val content: String,
    val date: String,
    val images: List<Image>? = emptyList(),
    val pid: Int,
    val postType: String,
    val title: String,
    val uid: Int,
    val writerImageURL: String? = null,
    val writerId: String,
    val writerNick: String,
    val likeCount: Int,
    val liked: Boolean
)