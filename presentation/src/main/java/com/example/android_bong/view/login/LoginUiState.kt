package com.example.android_bong.view.login

data class LoginUiState(
    val id: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val successToSignIn: Boolean = false,
    val userMessage: String? = null
) {
    val isInputValid: Boolean
        get() = isIDValid && isPasswordValid

    private val isIDValid: Boolean
        get() = id.length >= 4

    private val isPasswordValid: Boolean
        get() = password.length >= 6

    val showEmailError: Boolean
        get() = id.isNotEmpty() && !isIDValid

    val showPasswordError: Boolean
        get() = password.isNotEmpty() && !isPasswordValid
}