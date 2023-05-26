package com.example.android_bong.view.main.community.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
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


    private var postFetchJob: Job? = null
    private var commentFetchJob: Job? = null

    fun bind(postId: Int) {
        postDetailBind(postId)
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


}