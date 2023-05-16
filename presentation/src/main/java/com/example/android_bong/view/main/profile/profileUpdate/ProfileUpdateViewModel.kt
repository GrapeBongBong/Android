package com.example.android_bong.view.main.profile.profileUpdate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUpdateUiState())
    val uiState = _uiState.asStateFlow()


    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }
}