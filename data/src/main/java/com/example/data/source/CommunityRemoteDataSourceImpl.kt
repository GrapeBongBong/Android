package com.example.data.source

import com.example.data.api.CommunityApi
import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import com.example.data.model.community.CommunityRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class CommunityRemoteDataSourceImpl @Inject constructor(
    private val api: CommunityApi
) : CommunityRemoteDataSource {

    companion object {
        private const val IMAGE_KEY = "image"
    }

    override suspend fun getAll(): Response<List<CommunityDto>> = api.getAll()

    override suspend fun createPost(
        communityRequestBody: CommunityRequestBody,
        profileImage: File?
    ): Response<ResponseBody> {
        val image = if (profileImage != null) {
            MultipartBody.Part.createFormData(
                IMAGE_KEY,
                profileImage.name,
                profileImage.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        } else {
            MultipartBody.Part.createFormData(IMAGE_KEY, "")
        }
        return api.createPost(
            communityRequestBody = communityRequestBody
        )
    }

    override suspend fun deletePost(postId: Int): Response<ResponseBody> =
        api.deletePosts(postId = postId)

    override suspend fun updatePost(postId: Int): Response<ResponseBody> =
        api.updatePost(postId = postId)

}