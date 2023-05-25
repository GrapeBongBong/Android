package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityRequestBody
import retrofit2.Response
import retrofit2.http.*

interface CommunityApi {

    @GET("/anonymous/posts")
    suspend fun getAll(
    ): Response<ResponseBody>

    @POST("/anonymous/post")
    suspend fun createPost(
        @Body communityRequestBody: CommunityRequestBody
    ): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "/anonymous/delete/{postId}", hasBody = true)
    suspend fun deletePosts(
        @Path("postId") postId: Int
    ): Response<ResponseBody>

    @PUT("/anonymous/post/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Int
    ): Response<ResponseBody>


}