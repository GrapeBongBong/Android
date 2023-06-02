package com.example.android_bong.view.main.talentexchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.domain.usecase.post.GetAllExchangePostWithFilterUseCase
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
class TalentExchangeViewModel @Inject constructor(
    private val getAllExchangePostWithFilterUseCase: GetAllExchangePostWithFilterUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TalentExchangeUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null


    fun updateGiveCate(giveCate: String?) {
        _uiState.update { it.copy(giveCate = giveCate) }
    }

    fun updateTakeCate(takeCate: String?) {
        _uiState.update { it.copy(takeCate = takeCate) }
    }


    fun fetchPosts() {
        fetchJob?.cancel()
        if (uiState.value.giveCate != null && uiState.value.takeCate != null) {
            fetchJob = viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                val result = getAllExchangePostWithFilterUseCase(
                    giveCate = uiState.value.giveCate!!,
                    takeCate = uiState.value.takeCate!!
                )
                val userId = getUserUseCase()!!.uid
                if (result.isSuccess) {
                    _uiState.update { data ->
                        val posts = result.getOrNull()!!.map {
                            it.toUiState(userId)
                        }
                        val formatter =
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // 날짜 별로 정렬
                        val sortedList = posts.sortedByDescending {
                            LocalDateTime.parse(it.date, formatter)
                        }
                        data.copy(
                            posts = sortedList,
                            isLoadingSuccess = true,
                            isLoading = false,
                            giveCate = null,
                            takeCate = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            userMessage = result.exceptionOrNull()!!.localizedMessage,
                            isLoading = false,
                            giveCate = null,
                            takeCate = null
                        )
                    }
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }
}