package com.example.android_bong.view.main.talentexchange.detail

import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState

data class TalentExchangeDetailUiState(
    val userMessage: String? = null,
    val comments: List<CommentItemUiState> = emptyList(),
    val postId: Int? = null,
    val postContent: String = "",
    val isCommentLoadingSuccess: Boolean = false,
    val commentId: Int? = null,
    val postDetail: TalentExchangeItemUiState? = null,
) {
    val isValidComment: Boolean
        get() = postContent.isNotEmpty()
}

data class CommentItemUiState(
    val commentId: Int,
    val postId: Int,
    val content: String,
    val date: String,
    val userId: Int,
    val isMine: Boolean
)