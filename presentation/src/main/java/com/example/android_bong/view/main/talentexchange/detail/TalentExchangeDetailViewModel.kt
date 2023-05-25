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

    private val _talentExchangeDetailUiState = MutableStateFlow(TalentExchangeDetailUiState())
    val talentExchangeDetailUiState = _talentExchangeDetailUiState.asStateFlow()

    private val _commentUiState = MutableStateFlow(CommentUiState())
    val commentUiState = _commentUiState.asStateFlow()

    /**
     * 포스트 디테일 부분
     */

    fun postDetailBind(postId: Int) {
        viewModelScope.launch {
            _talentExchangeDetailUiState.update {
                it.copy(
                    postId = postId
                )
            }
            val result = getAllExchangePostUseCase()
            val foundPost = result.getOrNull()!!.find { it.pid == postId }
            val userId = getUserUseCase()!!.uid
            _talentExchangeDetailUiState.update {
                if (foundPost != null) {
                    it.copy(postDetail = foundPost.toUiState(userId))
                } else {
                    it.copy(userMessage = "해당 포스트를 찾지 못하였습니다.")
                }
            }
        }
    }

    fun postDetailUserMessageShown() {
        _talentExchangeDetailUiState.update { it.copy(userMessage = null) }
    }


    /**
     * 댓글 부분
     */

    fun fetchComments() {
        val postId = _talentExchangeDetailUiState.value.postId
        if (postId != null) {
            viewModelScope.launch {
                _commentUiState.update {
                    it.copy(
                        isLoading = true
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
    }

    fun updateCommentContent(content: String) {
        _commentUiState.update { it.copy(commentContent = content) }
    }


    fun createComment() {
        val content = _commentUiState.value.commentContent
        val postId = _commentUiState.value.postId!!
        viewModelScope.launch {
            _commentUiState.update {
                it.copy(isLoading = true)
            }
            val result = createCommentUseCase(postId = postId, content = content)
            if (result.isSuccess) {
                _commentUiState.update {
                    it.copy(
                        userMessage = result.getOrNull()!!,
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

    fun commentUserMessageShown() {
        _commentUiState.update { it.copy(userMessage = null) }
    }

}