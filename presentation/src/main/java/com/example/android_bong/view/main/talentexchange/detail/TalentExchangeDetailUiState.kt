package com.example.android_bong.view.main.talentexchange.detail

import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState

data class TalentExchangeDetailUiState(
    val userMessage: String? = null,
    val postId: Int? = null,
    val postDetail: TalentExchangeItemUiState? = null
)

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


data class CommentItemUiState(
    val commentId: Int,
    val postId: Int,
    val content: String,
    val date: String,
    val userId: Int,
    val isMine: Boolean
)