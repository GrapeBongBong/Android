package com.example.android_bong.view.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.R
import com.example.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchUserTemperature()
    }

    private fun fetchUserTemperature() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (getUserUseCase() != null) {
                _uiState.update {
                    it.copy(
                        currentUser = getUserUseCase()!!
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        userMessage = R.string.unable_to_retrieve_member_information
                    )
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }
}