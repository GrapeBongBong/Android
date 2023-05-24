package com.example.android_bong.view.main.profile.profileUpdate

import com.example.domain.model.user.User

data class ProfileUpdateUiState(
    val currentUser: User? = null,
    val userMessage: String? = null,
    val nickName: String = "",
    val address: String = "",
    val updatingIsSuccess: Boolean = false,
    val isLoading: Boolean = false,
)