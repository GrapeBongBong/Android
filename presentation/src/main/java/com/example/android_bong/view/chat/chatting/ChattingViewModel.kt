package com.example.android_bong.view.chat.chatting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.chatting.SuccessMatchingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val successMatchingUseCase: SuccessMatchingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChattingUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun bind(postId: Int, roomId: Int, roomTitle: String) {
        _uiState.update {
            it.copy(postId = postId, roomTitle = roomTitle, roomId = roomId)
        }
    }


    fun clickSuccess() {
        val postId = uiState.value.postId!!
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = successMatchingUseCase(postId)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(userMessage = result.getOrNull())
                }
            } else {
                _uiState.update {
                    it.copy(
                        userMessage = result.exceptionOrNull()!!.localizedMessage
                    )
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }

}