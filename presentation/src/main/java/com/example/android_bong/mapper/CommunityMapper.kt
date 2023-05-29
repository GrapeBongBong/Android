package com.example.android_bong.mapper

import com.example.android_bong.view.main.community.CommunityItemUiState
import com.example.domain.model.community.CommunityPost

fun CommunityPost.toUiState(userId: Int) = CommunityItemUiState(
    content = content,
    date = date,
    image = image,
    pid = pid,
    postType = postType,
    title = title,
    uid = uid,
    writerId = writerId,
    writerNick = writerNick,
    isMine = (uid == userId),
    writerImageURL = writerImageURL,
    liked = liked,
    likeCount = likeCount
)