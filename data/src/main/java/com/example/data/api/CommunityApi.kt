package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import com.example.data.model.community.CommunityRequestBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CommunityApi {

    @GET("/anonymous/posts")
    suspend fun getAll(
    ): Response<List<CommunityDto>>

    @Multipart
    @POST("/anonymous/post")
    suspend fun createPost(
        @Part("anonymousPostDTO") requestBody: RequestBody
    ): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "/anonymous/delete/{postId}", hasBody = true)
    suspend fun deletePosts(
        @Path("postId") postId: Int
    ): Response<ResponseBody>

    @PUT("/anonymous/post/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Int,
        @Body communityRequestBody: CommunityRequestBody
    ): Response<ResponseBody>


    @POST("/anonymous/{postId}/like")
    suspend fun clickLikeCommunity(
        @Path("postId") postId: Int
    ): Response<ResponseBody>

    @POST("/anonymous/{postId}/unlike")
    suspend fun clickUnLikeCommunity(
        @Path("postId") postId: Int
    ): Response<ResponseBody>

}