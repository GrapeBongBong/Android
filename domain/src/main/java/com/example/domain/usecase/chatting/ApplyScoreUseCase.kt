package com.example.domain.usecase.chatting

import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class ApplyScoreUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        postId: Int,
        score: Int
    ) = repository.applyScore(postId = postId, score = score)
}