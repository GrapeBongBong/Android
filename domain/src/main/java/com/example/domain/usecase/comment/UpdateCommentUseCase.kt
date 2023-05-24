package com.example.domain.usecase.comment

import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class UpdateCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(
        postId: Int,
        commentId: Int,
        content: String
    ) = repository.updateComment(
        postId = postId,
        commentId = commentId,
        content = content,
    )
}