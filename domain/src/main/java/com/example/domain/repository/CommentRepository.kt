package com.example.domain.repository

import com.example.domain.model.comment.Comment

interface CommentRepository {

    suspend fun getAll(postId: Int): Result<List<Comment>>

    suspend fun createComment(
        postId: Int,
        content: String
    ): Result<String>

    suspend fun deleteComment(postId: Int, commentId: Int): Result<Unit>

    suspend fun updateComment(postId: Int, commentId: Int, content: String): Result<Unit>

}