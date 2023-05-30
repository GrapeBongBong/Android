package com.example.data.mapper

import com.example.data.model.community.CommunityDto
import com.example.domain.model.community.CommunityPost

fun CommunityDto.toEntity() = CommunityPost(
    content = content,
    date = date,
    image = image?.map { it.toEntity() },
    pid = pid,
    postType = postType,
    title = title,
    uid = uid,
    writerId = writerId,
    writerNick = writerNick,
    writerImageURL = writerImageURL,
    liked = liked,
    likeCount = likeCount
)