package com.example.domain.usecase.comment

import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(
        postId: Int,
        content: String
    ) = repository.createComment(
        postId = postId,
        content = content,
    )
}