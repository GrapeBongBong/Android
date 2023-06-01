package com.example.domain.usecase.post

import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class GetPopularExchangePostUseCase @Inject constructor(
    private val repository: ExchangePostRepository
) {
    suspend operator fun invoke() = repository.getPopularExchangePost()
}