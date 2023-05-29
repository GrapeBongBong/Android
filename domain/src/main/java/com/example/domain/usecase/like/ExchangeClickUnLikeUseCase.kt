package com.example.domain.usecase.like

import com.example.domain.repository.ExchangePostRepository
import javax.inject.Inject

class ExchangeClickUnLikeUseCase @Inject constructor(
    private val repository: ExchangePostRepository
) {
    suspend operator fun invoke(postId: Int) = repository.clickUnLike(postId = postId)
}