package com.example.android_bong.view.signUp

data class SignUpUiState(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val nickName: String = "",
    val id: String = "",
    val address: String = "",
    val gender: String = "",
    val birth: String = "",
    val password: String = "",
    val passwordCheck: String = "",
    val userMessage: String? = null,
    val successToSignUp: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isInputValid: Boolean
        get() = isIdValid && isNameValid && isPasswordValid && isPasswordCheckValid && isPhoneNumberValid

    private val isNameValid: Boolean
        get() = name.length >= 3

    private val isNickNameValid: Boolean
        get() = nickName.length >= 4

    private val isPasswordValid: Boolean
        get() = password.length >= 6

    private val isPasswordCheckValid: Boolean
        get() = password == passwordCheck

    private val isIdValid: Boolean
        get() = id.length >= 4

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

    val showIdError: Boolean
        get() = id.isNotEmpty() && !isIdValid

    val showNameError: Boolean
        get() = name.isNotEmpty() && !isNameValid

    val showPhoneNumberError: Boolean
        get() = phoneNumber.isNotEmpty() && !isPhoneNumberValid

    val showNickNameError: Boolean
        get() = nickName.isNotEmpty() && !isNickNameValid

    val showEmailError: Boolean
        get() = email.isNotEmpty() && !isEmailValid

    val showPasswordError: Boolean
        get() = password.isNotEmpty() && !isPasswordValid

    val showPasswordCheckError: Boolean
        get() = passwordCheck.isNotEmpty() && !isPasswordCheckValid

}