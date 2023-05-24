package com.example.android_bong.view.main.profile

import com.example.domain.model.user.User

data class ProfileUiState(
    val currentUser: User? = null,
    val userMessage: Int? = null
)