package com.example.domain.usecase.like

import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class ExchangeClickLikeUseCase @Inject constructor(
    private val repository: ExchangePostRepository
) {
    suspend operator fun invoke(postId: Int) = repository.clickLike(postId = postId)
}