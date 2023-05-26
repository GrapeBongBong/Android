package com.example.android_bong.view.main.community.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.android_bong.view.main.comment.CommentUiState
import com.example.domain.usecase.comment.CreateCommentUseCase
import com.example.domain.usecase.comment.DeleteCommentUseCase
import com.example.domain.usecase.comment.GetAllCommentUseCase
import com.example.domain.usecase.comment.UpdateCommentUseCase
import com.example.domain.usecase.community.GetAllCommunityPostUseCase
import com.example.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityDetailViewModel @Inject constructor(
    private val getAllCommunityPostUseCase: GetAllCommunityPostUseCase,
    private val getUserUseCase: GetUserUseCase,

    private val getAllCommentUseCase: GetAllCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val updateCommentUseCase: UpdateCommentUseCase,
    private val createCommentUseCase: CreateCommentUseCase
) : ViewModel() {

    private val _communityDetailUiState = MutableStateFlow(CommunityDetailUiState())
    val communityDetailUiState = _communityDetailUiState.asStateFlow()

    private val _commentUiState = MutableStateFlow(CommentUiState())
    val commentUiState = _commentUiState.asStateFlow()

    private var postFetchJob: Job? = null
    private var commentFetchJob: Job? = null

    fun bind(postId: Int) {
        postDetailBind(postId)
        commentsBind(postId)
    }

    private fun postDetailBind(postId: Int) {
        postFetchJob?.cancel()
        postFetchJob = viewModelScope.launch {
            _communityDetailUiState.update {
                it.copy(
                    postId = postId
                )
            }
            val result = getAllCommunityPostUseCase()
            val foundPost = result.getOrNull()!!.find { it.pid == postId }
            val userId = getUserUseCase()!!.uid
            _communityDetailUiState.update {
                if (foundPost != null) {
                    it.copy(postDetail = foundPost.toUiState(userId))
                } else {
                    it.copy(userMessage = "해당 포스트를 찾지 못하였습니다.")
                }
            }
        }
    }


    fun postDetailUserMessageShown() {
        _communityDetailUiState.update { it.copy(userMessage = null) }
    }

    /**
     * 댓글 부분
     */

    private fun commentsBind(postId: Int) {
        commentFetchJob?.cancel()
        commentFetchJob = viewModelScope.launch {
            _commentUiState.update {
                it.copy(
                    isLoading = true, postId = postId
                )
            }
            val result = getAllCommentUseCase(postId = postId)
            val userId = getUserUseCase()!!.uid
            if (result.isSuccess) {
                _commentUiState.update { data ->
                    data.copy(
                        comments = result.getOrNull()!!.map { it.toUiState(userId) },
                        isLoadingSuccess = true,
                        isLoading = false
                    )
                }
            } else {
                _commentUiState.update {
                    it.copy(
                        userMessage = result.exceptionOrNull()!!.localizedMessage,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun createComment() {
        val content = commentUiState.value.commentContent
        val postId = commentUiState.value.postId!!
        viewModelScope.launch {
            val result = createCommentUseCase(postId = postId, content = content)
            if (result.isSuccess) {
                commentsBind(postId)
            } else {
                _commentUiState.update {
                    it.copy(
                        userMessage = result.getOrNull()!!
                    )
                }
            }
        }
    }

    fun updateCommentContent(content: String) {
        _commentUiState.update { it.copy(commentContent = content) }
    }

    fun commentUserMessageShown() {
        _commentUiState.update { it.copy(userMessage = null) }
    }


}