package com.example.data.source

import com.example.data.api.CommunityApi
import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class CommunityRemoteDataSourceImpl @Inject constructor(
    private val api: CommunityApi
) : CommunityRemoteDataSource {

    companion object {
        private const val IMAGE_KEY = "images"
    }

    override suspend fun getAll(): Response<List<CommunityDto>> = api.getAll()

    override suspend fun createPost(
        title: String,
        content: String,
        postImages: File?
    ): Response<ResponseBody> {

        val titleRequestBody: RequestBody = title.toRequestBody()
        val contentRequestBody: RequestBody = content.toRequestBody()

        val imagePart: MultipartBody.Part? = postImages?.let {
            val requestFile: RequestBody =
                it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(IMAGE_KEY, it.name, requestFile)
        }

        return api.createPost(
            titleRequestBody,
            contentRequestBody,
            imagePart
        )
    }


    override suspend fun deletePost(postId: Int): Response<ResponseBody> =
        api.deletePosts(postId = postId)

    override suspend fun updatePost(postId: Int): Response<ResponseBody> =
        api.updatePost(postId = postId)

}