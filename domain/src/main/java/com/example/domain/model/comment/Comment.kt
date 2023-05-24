package com.example.domain.model.comment

data class Comment(
    val commentId: Int,
    val postId: Int,
    val content: String,
    val date: String,
    val userId: Int
)