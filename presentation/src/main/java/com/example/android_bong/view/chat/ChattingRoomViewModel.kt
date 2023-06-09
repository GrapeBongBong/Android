package com.example.android_bong.view.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bong.mapper.toUiState
import com.example.domain.usecase.chatting.GetAllChatRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChattingRoomViewModel @Inject constructor(
    private val getAllChatRoomUseCase: GetAllChatRoomUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChattingRoomUiSate())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun bind(postId: Int) {
        fetchRooms(postId)
    }

    private fun fetchRooms(postId: Int) {
        fetchJob?.cancel()
        _uiState.update {
            it.copy(postId = postId)
        }
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getAllChatRoomUseCase(postId = postId)
            if (result.isSuccess) {
                _uiState.update { data ->
                    val posts = result.getOrNull()!!.map {
                        it.toUiState()
                    }
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // 날짜 별로 정렬
                    val sortedList = posts.sortedByDescending {
                        LocalDateTime.parse(it.date, formatter)
                    }
                    data.copy(
                        rooms = sortedList,
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


    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }

}