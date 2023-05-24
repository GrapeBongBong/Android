package com.example.android_bong.view.main.talentexchange.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.domain.usecase.comment.CreateCommentUseCase
import com.example.domain.usecase.comment.DeleteCommentUseCase
import com.example.domain.usecase.comment.GetAllCommentUseCase
import com.example.domain.usecase.comment.UpdateCommentUseCase
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
class TalentExchangeDetailViewModel @Inject constructor(
    private val getAllExchangePostUseCase: GetAllExchangePostUseCase,
    private val getUserUseCase: GetUserUseCase,

    private val getAllCommentUseCase: GetAllCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val updateCommentUseCase: UpdateCommentUseCase,
    private val createCommentUseCase: CreateCommentUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TalentExchangeDetailUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun postDetailBind(postId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    postId = postId
                )
            }
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

    fun updateContent(content: String) {
        _uiState.update { it.copy(postContent = content) }
    }

    fun fetchComments() {
        val postId = uiState.value.postId
        if (postId != null) {
            viewModelScope.launch {
                val result = getAllCommentUseCase(postId = postId)
                val userId = getUserUseCase()!!.uid
                if (result.isSuccess) {
                    _uiState.update { data ->
                        data.copy(
                            comments = result.getOrNull()!!.map { it.toUiState(userId) },
                            isCommentLoadingSuccess = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(userMessage = result.exceptionOrNull()!!.localizedMessage)
                    }
                }
            }
        } else {
            _uiState.update {
                it.copy(userMessage = "해당 포스트를 찾을 수 없어요.")
            }
        }
    }

    fun createComment() {
        val content = uiState.value.postContent
        val postId = uiState.value.postId!!
        viewModelScope.launch {
            val result = createCommentUseCase(postId = postId, content = content)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        userMessage = result.getOrNull()!!.toString()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(userMessage = result.exceptionOrNull()!!.localizedMessage)
                }
            }
        }
        fetchComments()
    }

    fun deleteComment(uiState: CommentItemUiState) {
        viewModelScope.launch {

        }
    }

    fun updateComment(uiState: CommentItemUiState) {
        viewModelScope.launch {

        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }

}