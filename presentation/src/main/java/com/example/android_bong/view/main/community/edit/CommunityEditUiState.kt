package com.example.android_bong.view.main.community.edit

import com.example.android_bong.view.main.community.CommunityItemUiState

data class CommunityEditUiState(
    val userMessage: String? = null,
    val title: String = "",
    val content: String = "",
    val postId: Int? = null,
    val postDetail: CommunityItemUiState? = null,
    val bindingSuccess: Boolean = false,
    val isSuccessUpdating: Boolean = false,
    val isLoading: Boolean = false
) {
    val isInputValid: Boolean
        get() = isValidTitle && isValidContent

    private val isValidTitle: Boolean
        get() = title.isNotEmpty()

    private val isValidContent: Boolean
        get() = content.isNotEmpty()
}