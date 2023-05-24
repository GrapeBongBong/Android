package com.example.data.api

import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface CommunityApi {

    @GET("/anonymous/posts")
    suspend fun getAll(

    )

    @HTTP(method = "DELETE", path = "/anonymous/delete/{postId}", hasBody = true)
    suspend fun deletePosts(

    )

    @PUT("/anonymous/post/{postId}")
    suspend fun updatePost(

    )

    @POST("/anonymous/post")
    suspend fun createPost(

    )


}