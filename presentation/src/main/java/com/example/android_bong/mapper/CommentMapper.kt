package com.example.android_bong.mapper

import com.example.android_bong.view.main.comment.CommentItemUiState
import com.example.domain.model.comment.Comment

fun Comment.toUiState(uid: Int) = CommentItemUiState(
    commentId = commentId,
    postId = postId,
    userId = userId,
    content = content,
    date = date,
    isMine = (userId == uid)
)