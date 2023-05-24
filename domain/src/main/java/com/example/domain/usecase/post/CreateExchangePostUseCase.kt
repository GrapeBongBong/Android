package com.example.domain.usecase.post

import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class CreateExchangePostUseCase @Inject constructor(
    private val repository: ExchangePostRepository
) {
    suspend operator fun invoke(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        days: MutableList<String>,
        timeZone: String
    ) = repository.createExchangePost(
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