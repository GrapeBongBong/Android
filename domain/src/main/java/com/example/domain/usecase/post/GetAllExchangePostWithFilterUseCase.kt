package com.example.domain.usecase.post

import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class GetAllExchangePostWithFilterUseCase @Inject constructor(
    private val repository: ExchangePostRepository
) {
    suspend operator fun invoke(
        takeCate: String,
        giveCate: String
    ) = repository.getAllWithFilter(
        giveCate = giveCate,
        takeCate = takeCate
    )
}