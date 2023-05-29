package com.example.domain.usecase.like

import com.example.domain.repository.CommunityRepository
import javax.inject.Inject

class CommunityClickLikeUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    suspend operator fun invoke(postId: Int) = repository.clickLike(postId = postId)
}