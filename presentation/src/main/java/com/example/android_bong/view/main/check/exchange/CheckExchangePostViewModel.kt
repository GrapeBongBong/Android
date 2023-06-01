package com.example.android_bong.view.main.check.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.domain.usecase.user.GetCompletedMatchesUseCase
import com.example.domain.usecase.user.GetMyExchangePostUseCase
import com.example.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CheckExchangePostViewModel @Inject constructor(
    private val myExchangePostUseCase: GetMyExchangePostUseCase,
    private val completedMatchesUseCase: GetCompletedMatchesUseCase,
    private val getUserUseCase: GetUserUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckExchangePostUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchMyExchangePosts() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = myExchangePostUseCase()
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _uiState.update { data ->
                    val posts = result.getOrNull()!!.map {
                        it.toUiState(userId)
                    }
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // 날짜 별로 정렬
                    val sortedList = posts.sortedByDescending {
                        LocalDateTime.parse(it.date, formatter)
                    }
                    data.copy(
                        posts = sortedList,
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

    fun fetchMyCompletedPosts() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = completedMatchesUseCase()
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _uiState.update { data ->
                    val posts = result.getOrNull()!!.map {
                        it.toUiState(userId)
                    }
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // 날짜 별로 정렬
                    val sortedList = posts.sortedByDescending {
                        LocalDateTime.parse(it.date, formatter)
                    }
                    data.copy(
                        posts = sortedList,
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