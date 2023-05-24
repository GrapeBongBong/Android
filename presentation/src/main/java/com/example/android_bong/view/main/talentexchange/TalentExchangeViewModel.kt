package com.example.android_bong.view.main.talentexchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.domain.usecase.post.GetAllExchangePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TalentExchangeViewModel @Inject constructor(
    private val getAllExchangePostUseCase: GetAllExchangePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TalentExchangeUiState())
    val uiState = _uiState.asStateFlow()


    private var fetchJob: Job? = null

    fun postDetailBind(postId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = getAllExchangePostUseCase()
            val foundPost = result.getOrNull()!!.find { it.pid == postId }

            _uiState.update {
                if (foundPost != null) {
                    it.copy(postDetail = foundPost.toUiState())
                } else {
                    it.copy(userMessage = "해당 포스트를 찾지 못하였습니다.")
                }
            }

        }
    }

    fun fetchPosts() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = getAllExchangePostUseCase()
            if (result.isSuccess) {
                _uiState.update { data ->
                    data.copy(
                        posts = result.getOrNull()!!.map { it.toUiState() },
                        isLoadingSuccess = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(userMessage = result.exceptionOrNull()!!.localizedMessage)
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }
}