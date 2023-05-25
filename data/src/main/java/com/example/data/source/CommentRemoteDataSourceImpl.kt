package com.example.data.source

import com.example.data.api.CommentApi
import com.example.data.model.ResponseBody
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
    ): Response<ResponseBody> =
        api.createComment(postId, createCommentRequestBody)

    override suspend fun deleteComment(postId: Int, commentId: Int): Response<ResponseBody> =
        api.deleteComment(postId, commentId)

    override suspend fun updateComment(
        postId: Int,
        commentId: Int,
        createCommentRequestBody: CreateCommentRequestBody
    ): Response<ResponseBody> =
        api.updateComment(postId, commentId, createCommentRequestBody)

}