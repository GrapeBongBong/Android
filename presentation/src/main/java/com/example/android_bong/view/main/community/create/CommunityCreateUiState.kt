package com.example.android_bong.view.main.community.create

import android.graphics.Bitmap

data class CommunityCreateUiState(
    val userMessage: String? = null,
    val title: String = "",
    val content: String = "",
    val image1: Bitmap? = null,
    val image2: Bitmap? = null,
    val image3: Bitmap? = null,
    val isSuccessPosting: Boolean = false,
    val isLoading: Boolean = false
) {

    val isInputValid: Boolean
        get() = isValidTitle && isValidContent

    private val isValidTitle: Boolean
        get() = title.isNotEmpty()

    private val isValidContent: Boolean
        get() = content.isNotEmpty()
}