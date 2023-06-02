package com.example.android_bong.view.main.talentexchange.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.post.UpdateExchangePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TalentExchangeEditViewModel @Inject constructor(
    private val updateExchangePostUseCase: UpdateExchangePostUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TalentExchangeEditUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun bind(postId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    postId = postId
                )
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun updateGiveCate(giveCate: String) {
        _uiState.update { it.copy(giveCate = giveCate) }
    }

    fun updateTakeCate(takeCate: String) {
        _uiState.update { it.copy(takeCate = takeCate) }
    }

    fun updateGiveTalent(giveTalent: String) {
        _uiState.update { it.copy(giveTalent = giveTalent) }
    }

    fun updateTakeTalent(takeTalent: String) {
        _uiState.update { it.copy(takeTalent = takeTalent) }
    }

    fun updatePossibleDay(day: String) {
        _uiState.update {
            it.copy(
                possibleDays = mutableListOf(day)
            )
        }
    }

    fun updatePossibleTime(time: String) {
        _uiState.update {
            it.copy(possibleTimeZone = time)
        }
    }

    fun updatePost() {
        val postId = uiState.value.postId!!
        val title = uiState.value.title
        val content = uiState.value.content
        val giveCate = uiState.value.giveCate
        val takeCate = uiState.value.takeCate
        val giveTalent = uiState.value.giveTalent
        val takeTalent = uiState.value.takeTalent
        val days = uiState.value.possibleDays
        val timeZone = uiState.value.possibleTimeZone
        _uiState.update {
            it.copy(isLoading = true)
        }
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = updateExchangePostUseCase(
                postId = postId,
                title = title,
                content = content,
                giveCate = giveCate,
                takeCate = takeCate,
                giveTalent = giveTalent,
                takeTalent = takeTalent,
                days = days,
                timeZone = timeZone
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        userMessage = result.getOrNull(),
                        isLoading = false,
                        isSuccessUpdating = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        userMessage = result.exceptionOrNull()?.localizedMessage,
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