package com.example.data.source

import com.example.data.model.ResponseBody
import com.example.data.model.comment.CommentsDto
import com.example.data.model.comment.CreateCommentRequestBody
import retrofit2.Response

interface CommunityRemoteDataSource {

    suspend fun getAll(postId: Int): Response<CommentsDto>

    suspend fun createPost(
        postId: Int,
        createCommentRequestBody: CreateCommentRequestBody
    ): Response<ResponseBody>

    suspend fun deletePost(postId: Int, commentId: Int): Response<ResponseBody>

    suspend fun updatePost(
        postId: Int,
        commentId: Int,
        createCommentRequestBody: CreateCommentRequestBody
    ): Response<ResponseBody>

}