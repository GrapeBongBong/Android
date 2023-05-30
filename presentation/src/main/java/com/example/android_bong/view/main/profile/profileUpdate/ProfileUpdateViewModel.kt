package com.example.android_bong.view.main.profile.profileUpdate

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.user.ConvertBitmapToFileUseCase
import com.example.domain.usecase.user.GetUserUseCase
import com.example.domain.usecase.user.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val convertBitmapToFileUseCase: ConvertBitmapToFileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUpdateUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    var selectedImage: Bitmap? = null

    init {
        bind()
    }

    fun updateNickName(nickName: String) {
        _uiState.update { it.copy(nickName = nickName) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun bind() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(currentUser = getUserUseCase()!!)
            }
        }
    }

    fun updateProfile() {
        val nickName = uiState.value.nickName
        val email = uiState.value.email
        val phoneNumber = uiState.value.phoneNumber
        val password = uiState.value.password
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = updateUserInfoUseCase(
                userId = uiState.value.currentUser!!.uid,
                nickName = nickName,
                email = email,
                phoneNumber = phoneNumber,
                password = password,
                profileImage = selectedImage?.let {
                    convertBitmapToFileUseCase(it, "ProfileImage.jpg")
                }
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        updatingIsSuccess = true,
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
}