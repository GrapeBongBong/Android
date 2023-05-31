package com.example.domain.usecase.chatting

import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class GetAllChatRoomUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        postId: Int
    ) = repository.getAllThisPostChatRoom(postId = postId)
}