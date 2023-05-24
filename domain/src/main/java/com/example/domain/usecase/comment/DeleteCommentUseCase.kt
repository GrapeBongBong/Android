package com.example.domain.usecase.comment

import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(
        postId: Int,
        commentId: Int
    ) = repository.deleteComment(
        postId = postId,
        commentId = commentId
    )
}