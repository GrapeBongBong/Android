package com.example.android_bong.view.main.comment


data class CommentUiState(
    val commentContent: String = "",
    val postId: Int? = null,
    val comments: List<CommentItemUiState> = emptyList(),
    val userMessage: String? = null,
    val commentId: Int? = null,
    val isLoadingSuccess: Boolean = false,
    val isLoading: Boolean = false
) {
    val isValidComment: Boolean
        get() = commentContent.isNotEmpty()
}