package com.example.domain.usecase.post

import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class GetAllExchangePostUseCase @Inject constructor(
    private val repository: ExchangePostRepository
) {
    operator fun invoke() = repository.getAll()
}