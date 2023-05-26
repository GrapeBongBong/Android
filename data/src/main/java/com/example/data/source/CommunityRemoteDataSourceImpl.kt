package com.example.data.source

import com.example.data.api.CommunityApi
import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import com.example.data.model.community.CommunityRequestBody
import retrofit2.Response
import javax.inject.Inject

class CommunityRemoteDataSourceImpl @Inject constructor(
    private val api: CommunityApi
) : CommunityRemoteDataSource {
    override suspend fun getAll(): Response<List<CommunityDto>> = api.getAll()

    override suspend fun createPost(communityRequestBody: CommunityRequestBody): Response<ResponseBody> =
        api.createPost(
            communityRequestBody = communityRequestBody
        )

    override suspend fun deletePost(postId: Int): Response<ResponseBody> =
        api.deletePosts(postId = postId)

    override suspend fun updatePost(postId: Int): Response<ResponseBody> =
        api.updatePost(postId = postId)

}