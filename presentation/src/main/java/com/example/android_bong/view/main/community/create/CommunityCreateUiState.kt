package com.example.android_bong.view.main.community.create

data class CommunityCreateUiState(
    val userMessage: String? = null,
    val title: String = "",
    val content: String = "",
    val isSuccessPosting: Boolean = false
) {

    val isInputValid: Boolean
        get() = isValidTitle && isValidContent

    private val isValidTitle: Boolean
        get() = title.isNotEmpty()

    private val isValidContent: Boolean
        get() = content.isNotEmpty()
}