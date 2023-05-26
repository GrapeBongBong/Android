package com.example.android_bong.view.main.community.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.community.CreateCommunityPostUseCase
import com.example.domain.usecase.community.UpdateCommunityPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityCreateViewModel @Inject constructor(
    private val createCommunityPostUseCase: CreateCommunityPostUseCase,
    private val updateCommunityPostUseCase: UpdateCommunityPostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityCreateUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun createPost() {
        val title = uiState.value.title
        val content = uiState.value.content
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = createCommunityPostUseCase(
                title = title,
                content = content
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        userMessage = "게시물이 생성되었습니다.", isSuccessPosting = true
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