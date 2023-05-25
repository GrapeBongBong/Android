package com.example.android_bong.view.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun updateID(id: String) {
        _uiState.update { it.copy(id = id) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun updatePasswordCheck(passwordCheck: String) {
        _uiState.update { it.copy(passwordCheck = passwordCheck) }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateNickName(nickName: String) {
        _uiState.update { it.copy(nickName = nickName) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateBirth(birth: String) {
        _uiState.update { it.copy(birth = birth) }
    }

    fun updateGender(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }


    fun signUp() {
        val name = uiState.value.name
        val phoneNumber = uiState.value.phoneNumber
        val email = uiState.value.email
        val nickName = uiState.value.nickName
        val id = uiState.value.id
        val address = uiState.value.address
        val gender = uiState.value.gender
        val birth = uiState.value.birth
        val password = uiState.value.password
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = signUpUseCase(
                id = id,
                password = password,
                name = name,
                nickName = nickName,
                birth = birth,
                email = email,
                phoneNum = phoneNumber,
                address = "서울시 성북구",
                gender = gender
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        successToSignUp = true,
                        isLoading = false,
                        userMessage = result.getOrNull()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        userMessage = result.exceptionOrNull()!!.localizedMessage,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }

    fun showUserMessage(message: String) {
        _uiState.update { it.copy(userMessage = message) }
    }

}