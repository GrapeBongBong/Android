package com.example.domain.usecase.community

import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class GetAllCommunityPostUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(postId: Int) = repository.getAll(postId = postId)
}