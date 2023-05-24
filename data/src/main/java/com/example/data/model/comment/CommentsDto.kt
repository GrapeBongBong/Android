package com.example.data.model.comment

data class CommentsDto(
    val contents: List<CommentDto>? = emptyList()
)