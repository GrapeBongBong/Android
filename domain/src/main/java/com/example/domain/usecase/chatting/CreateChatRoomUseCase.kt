package com.example.domain.usecase.chatting

import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class CreateChatRoomUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        exchangePostId: Int,
        applicantId: String
    ) = repository.createChatRoom(exchangePostId = exchangePostId, applicantId = applicantId)
}
