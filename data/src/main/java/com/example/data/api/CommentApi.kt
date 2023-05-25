package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.comment.CommentsDto
import com.example.data.model.comment.CreateCommentRequestBody
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {

    @GET("/{postId}/comments")
    suspend fun getAllComment(
        @Path("postId") postId: Int
    ): Response<CommentsDto>

    @POST("/{postId}/comment")
    suspend fun createComment(
        @Path("postId") postId: Int,
        @Body createCommentRequestBody: CreateCommentRequestBody
    ): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "/{postId}/comment/delete/{commentId}", hasBody = true)
    suspend fun deleteComment(
        @Path("postId") postId: Int,
        @Path("commentId") commentId: Int
    ): Response<ResponseBody>

    @PUT("/{postId}/comment/{commentId}")
    suspend fun updateComment(
        @Path("postId") postId: Int,
        @Path("commentId") commentId: Int,
        @Body createCommentRequestBody: CreateCommentRequestBody
    ): Response<ResponseBody>

}