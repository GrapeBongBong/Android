package com.example.android_bong.view.chat.chatting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.chat.ChatMessage
import com.example.domain.repository.ChattingRepository
import com.example.domain.usecase.chatting.ApplyScoreUseCase
import com.example.domain.usecase.chatting.SuccessMatchingUseCase
import com.example.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val successMatchingUseCase: SuccessMatchingUseCase,
    private val applyScoreUseCase: ApplyScoreUseCase,
    private val getUserUseCase: GetUserUseCase,

    private val chattingRepository: ChattingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChattingUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun bind(postId: Int, roomId: Int, roomTitle: String) {
        _uiState.update {
            it.copy(
                postId = postId,
                roomTitle = roomTitle,
                roomId = roomId,
                senderId = getUserUseCase()!!.id
            )
        }
    }

    fun bindChatting(chatting: MutableList<ChatMessage>) {
        _uiState.update {
            it.copy(
                chatting = chatting
            )
        }
    }

    fun connectToWebSocket(roomId: Int) {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("onOpen", response.code.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                // 서버에서 텍스트 메시지를 받았을 때 처리하는 로직, 예: 기본 메시지 또는 이전 채팅 메시지 받기
                try {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    val jsonArray = JSONArray(text)
                    val chatMessages = ArrayList<ChatMessage>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        chatMessages.add(
                            ChatMessage(
                                roomId = jsonObject.getInt("roomId"),
                                senderId = jsonObject.getString("senderId"),
                                message = jsonObject.getString("message"),
                            )
                        )
                    }
                    bindChatting(chatMessages)
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    val json = JSONObject(text)
                    val chatMessage = ChatMessage(
                        roomId = json.getInt("roomId"),
                        senderId = json.getString("senderId"),
                        message = json.getString("message")
                    )
                    Log.d("ChatMessage", chatMessage.toString())
                    Log.d("uiState.value.chatting", uiState.value.chatting.toString())
                    uiState.value.chatting.add(chatMessage)
                    Log.d("uiState.value.chatting", uiState.value.chatting.toString())
                    val chatting = uiState.value.chatting
                    _uiState.update {
                        it.copy(
                            chatting = chatting, isLoading = false
                        )
                    }
                }

            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("onClosed", code.toString())
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d("onMessage", bytes.toString())
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("onFailure", response.toString())
            }
            // 오류 발생시 처리
        }
        chattingRepository.connect("ws://3.34.75.23:8080/ws/chat/${roomId}", listener)
    }

    fun disconnectFromWebSocket() {
        chattingRepository.disconnect()
    }

    fun sendMessage() {
        val senderId = uiState.value.senderId
        val roomId = uiState.value.roomId!!
        val message = uiState.value.myChatMessage
        viewModelScope.launch {
            chattingRepository.sendMessage(roomId = roomId, senderId = senderId, message = message)
        }
    }

    fun updateMyChatMessage(myChatMessage: String) {
        _uiState.update { it.copy(myChatMessage = myChatMessage) }
    }

    fun updateScore(score: Int) {
        _uiState.update { it.copy(score = score) }
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

    fun applyScore() {
        val postId = uiState.value.postId!!
        val score = uiState.value.score!!
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = applyScoreUseCase(postId = postId, score = score)
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
}