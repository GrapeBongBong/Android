package com.example.domain.usecase.post

import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class UpdateExchangePostUseCase @Inject constructor(
    private val repository: ExchangePostRepository
) {
    suspend operator fun invoke(
        postId: Int,
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        days: MutableList<String>,
        timeZone: String
    ) = repository.updateExchangePost(
        postId = postId,
        title = title,
        content = content,
        giveCate = giveCate,
        takeCate = takeCate,
        giveTalent = giveTalent,
        takeTalent = takeTalent,
        days = days,
        timeZone = timeZone
    )
}