package com.example.android_bong.view.main.comment

data class CommentItemUiState(
    val commentId: Int,
    val postId: Int,
    val content: String,
    val date: String,
    val userId: Int,
    val isMine: Boolean
)