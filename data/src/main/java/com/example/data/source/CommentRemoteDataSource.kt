package com.example.data.source

import com.example.data.model.comment.CommentsDto
import com.example.data.model.comment.CreateCommentRequestBody
import retrofit2.Response

interface CommentRemoteDataSource {

    suspend fun getAll(postId: Int): Response<CommentsDto>

    suspend fun createComment(postId: Int, createCommentRequestBody: CreateCommentRequestBody): Response<Unit>

    suspend fun deleteComment(postId: Int, userId: Int): Response<Unit>

    suspend fun updateComment(postId: Int, userId: Int): Response<Unit>

}