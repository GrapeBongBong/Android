package com.example.data.model.comment

data class CommentDto(
    val commentId: Int,
    val postId: Int,
    val content: String,
    val date: String,
    val userId: Int
)