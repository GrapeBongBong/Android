package com.example.data.source

import com.example.data.model.ResponseBody
import com.example.data.model.comment.CommentsDto
import com.example.data.model.comment.CreateCommentRequestBody
import retrofit2.Response

interface CommentRemoteDataSource {

    suspend fun getAll(postId: Int): Response<CommentsDto>

    suspend fun createComment(
        postId: Int,
        createCommentRequestBody: CreateCommentRequestBody
    ): Response<ResponseBody>

    suspend fun deleteComment(postId: Int, commentId: Int): Response<ResponseBody>

    suspend fun updateComment(
        postId: Int,
        commentId: Int,
        createCommentRequestBody: CreateCommentRequestBody
    ): Response<ResponseBody>

}