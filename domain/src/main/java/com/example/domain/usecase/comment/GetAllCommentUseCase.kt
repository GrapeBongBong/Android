package com.example.domain.usecase.comment

import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class GetAllCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(
        postId: Int
    ) = repository.getAll(
        postId = postId
    )
}