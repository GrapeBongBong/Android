package com.example.data.api

import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface CommentApi {

    @POST("/{postId}/comment")
    suspend fun createComment(

    )

    @HTTP(method = "DELETE", path ="/{postId}/comment/delete/{commentId}", hasBody = true)
    suspend fun deleteComment(

    )


    @GET("/{postId}/comments")
    suspend fun getAllComment(

    )

    @PUT("/{postId}/comment/{commentId}")
    suspend fun updateComment(

    )

}