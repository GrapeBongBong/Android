package com.example.android_bong.view.main.community.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.community.GetAllCommunityPostUseCase
import com.example.domain.usecase.community.UpdateCommunityPostUseCase
import com.example.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityEditViewModel @Inject constructor(
    private val updateCommunityPostUseCase: UpdateCommunityPostUseCase,
    private val getAllCommunityPostUseCase: GetAllCommunityPostUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityEditUiState())
    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

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

    fun updatePost() {
        val title = uiState.value.title
        val content = uiState.value.content
        val postId = uiState.value.postId
        _uiState.update {
            it.copy(isLoading = true)
        }
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = updateCommunityPostUseCase(
                postId = postId!!,
                title = title,
                content = content
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        userMessage = result.getOrNull(),
                        isSuccessUpdating = true,
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