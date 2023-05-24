package com.example.android_bong.view.main.talentexchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.domain.usecase.community.GetAllCommunityPostUseCase
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
    private val deleteExchangePostUseCase: DeleteExchangePostUseCase,
    private val getAllCommunityPostUseCase: GetAllCommunityPostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TalentExchangeUiState())
    val uiState = _uiState.asStateFlow()


    private var fetchJob: Job? = null

    fun postDetailBind(postId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = getAllExchangePostUseCase()
            val foundPost = result.getOrNull()!!.find { it.pid == postId }
            val userId = getUserUseCase()!!.uid
            _uiState.update {
                if (foundPost != null) {
                    it.copy(postDetail = foundPost.toUiState(userId))
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
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _uiState.update { data ->
                    data.copy(
                        posts = result.getOrNull()!!.map { it.toUiState(userId) },
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


    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            val result = getAllCommunityPostUseCase(postId = postId)
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _uiState.update { data ->
                    data.copy(comments = result.getOrNull()!!.map { it.toUiState(userId) },
                        isCommentLoadingSuccess = true
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