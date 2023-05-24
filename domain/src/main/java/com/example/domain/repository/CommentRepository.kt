package com.example.domain.repository

import com.example.domain.model.comment.Comment

interface CommentRepository {

    suspend fun getAll(postId: Int): Result<List<Comment>>

}