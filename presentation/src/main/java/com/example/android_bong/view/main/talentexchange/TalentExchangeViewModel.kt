package com.example.android_bong.view.main.talentexchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.domain.usecase.post.DeleteExchangePostUseCase
import com.example.domain.usecase.post.GetAllExchangePostUseCase
import com.example.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TalentExchangeViewModel @Inject constructor(
    private val getAllExchangePostUseCase: GetAllExchangePostUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val deleteExchangePostUseCase: DeleteExchangePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TalentExchangeUiState())
    val uiState = _uiState.asStateFlow()


    private var fetchJob: Job? = null

    fun fetchPosts() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getAllExchangePostUseCase()
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _uiState.update { data ->
                    data.copy(
                        posts = result.getOrNull()!!.map { it.toUiState(userId) },
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

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            val result = deleteExchangePostUseCase(postId = postId)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(userMessage = "삭제 되었습니다.")
                }
            } else {
                _uiState.update {
                    it.copy(userMessage = result.exceptionOrNull()!!.localizedMessage)
                }
            }
        }
    }

    fun updatePost() {

    }


    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }
}