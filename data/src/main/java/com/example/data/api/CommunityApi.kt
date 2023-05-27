package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import okhttp3.MultipartBody
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
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
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