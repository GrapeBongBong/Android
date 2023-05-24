package com.example.data.source

import com.example.data.api.CommentApi
import com.example.data.model.comment.CommentsDto
import com.example.data.model.comment.CreateCommentRequestBody
import retrofit2.Response
import javax.inject.Inject

class CommentRemoteDataSourceImpl @Inject constructor(
    private val api: CommentApi
) : CommentRemoteDataSource {
    override suspend fun getAll(postId: Int): Response<CommentsDto> = api.getAllComment(postId)

    override suspend fun createComment(
        postId: Int,
        createCommentRequestBody: CreateCommentRequestBody
    ): Response<Unit> =
        api.createComment(postId, createCommentRequestBody)

    override suspend fun deleteComment(postId: Int, userId: Int): Response<Unit> =
        api.deleteComment(postId, userId)

    override suspend fun updateComment(postId: Int, userId: Int): Response<Unit> =
        api.updateComment(postId, userId)

}