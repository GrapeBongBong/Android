package com.example.data.source

import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import com.example.data.model.community.CommunityRequestBody
import retrofit2.Response
import java.io.File

interface CommunityRemoteDataSource {

    suspend fun getAll(): Response<List<CommunityDto>>

    suspend fun createPost(
        communityRequestBody: CommunityRequestBody,
        profileImage: File?
    ): Response<ResponseBody>

    suspend fun deletePost(postId: Int): Response<ResponseBody>

    suspend fun updatePost(
        postId: Int
    ): Response<ResponseBody>

}