package com.example.android_bong.view.main.community.edit

data class CommunityEditUiState(
    val userMessage: String? = null,
    val title: String = "",
    val content: String = "",
    val postId: Int? = null,
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