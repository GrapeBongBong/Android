package com.example.android_bong.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.R
import com.example.android_bong.mapper.toUiState
import com.example.android_bong.view.main.home.HomeUiState
import com.example.android_bong.view.main.profile.ProfileUiState
import com.example.domain.usecase.community.GetPopularCommunityPostUseCase
import com.example.domain.usecase.post.GetPopularExchangePostUseCase
import com.example.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,

    private val getPopularCommunityPostUseCase: GetPopularCommunityPostUseCase,
    private val getPopularExchangePostUseCase: GetPopularExchangePostUseCase
) : ViewModel() {
    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchUserTemperature()
    }

    fun fetchUserTemperature() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (getUserUseCase() != null) {
                _profileUiState.update {
                    it.copy(
                        currentUser = getUserUseCase()
                    )
                }
            } else {
                _profileUiState.update {
                    it.copy(
                        userMessage = R.string.unable_to_retrieve_member_information
                    )
                }
            }
        }
    }

    fun userProfileMessageShown() {
        _profileUiState.update { it.copy(userMessage = null) }
    }


    fun bind() {
        fetchExchangePosts()
        fetchCommunityPosts()
    }

    private fun fetchExchangePosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getPopularExchangePostUseCase()
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _uiState.update { data ->
                    val posts = result.getOrNull()!!.map {
                        it.toUiState(userId)
                    }
                    data.copy(
                        exchangePosts = posts,
                        isLoadingSuccess = true,
                        isLoading = false
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

    private fun fetchCommunityPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getPopularCommunityPostUseCase()
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _uiState.update { data ->
                    val posts = result.getOrNull()!!.map {
                        it.toUiState(userId)
                    }
                    data.copy(
                        communityPosts = posts,
                        isLoadingSuccess = true,
                        isLoading = false
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