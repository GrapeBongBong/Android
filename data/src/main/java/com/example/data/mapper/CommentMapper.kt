package com.example.data.mapper

import com.example.data.model.comment.CommentDto
import com.example.domain.model.comment.Comment

fun CommentDto.toEntity() = Comment(
    commentId = commentId,
    postId = postId,
    userId = userId,
    content = content,
    date = date
)