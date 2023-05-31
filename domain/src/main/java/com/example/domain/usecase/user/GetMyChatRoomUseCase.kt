package com.example.domain.usecase.user

import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class GetMyChatRoomUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke() = repository.getAllMyChatRoom()
}