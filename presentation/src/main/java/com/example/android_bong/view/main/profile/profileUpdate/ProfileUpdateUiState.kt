package com.example.android_bong.view.main.profile.profileUpdate

import com.example.domain.model.user.User

data class ProfileUpdateUiState(
    val currentUser: User? = null,
    val userMessage: String? = null,
    val nickName: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val updatingIsSuccess: Boolean = false,
    val isLoading: Boolean = false,
) {

    val isInputValid: Boolean
        get() = isPasswordValid && isPhoneNumberValid && isPhoneNumberValid && isEmailValid

    private val isNickNameValid: Boolean
        get() = nickName.length >= 4

    private val isPasswordValid: Boolean
        get() = password.length >= 6

    private val isPhoneNumberValid: Boolean
        get() {
            return if (phoneNumber.isEmpty()) {
                false
            } else {
                android.util.Patterns.PHONE.matcher(phoneNumber).matches()
            }
        }

    private val isEmailValid: Boolean
        get() {
            return if (email.isEmpty()) {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        }


    val showPhoneNumberError: Boolean
        get() = phoneNumber.isNotEmpty() && !isPhoneNumberValid

    val showNickNameError: Boolean
        get() = nickName.isNotEmpty() && !isNickNameValid

    val showEmailError: Boolean
        get() = email.isNotEmpty() && !isEmailValid

    val showPasswordError: Boolean
        get() = password.isNotEmpty() && !isPasswordValid

}