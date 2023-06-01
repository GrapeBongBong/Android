package com.example.data.source

import com.example.data.api.CommunityApi
import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import com.example.data.model.community.CommunityRequestBody
import com.google.gson.Gson
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
        images: List<File?>
    ): Response<ResponseBody> {
        val data = CommunityRequestBody(title = title, content = content)

        // Gson을 사용하여 객체를 JSON 형식의 문자열로 변환
        val gson = Gson()
        val json = gson.toJson(data)

        val jsonRequestBody: RequestBody =
            json.toRequestBody("application/json".toMediaTypeOrNull())

        val imagesPart = images.map { image ->
            if (image != null) {
                MultipartBody.Part.createFormData(
                    IMAGE_KEY,
                    image.name,
                    image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
            } else {
                MultipartBody.Part.createFormData(IMAGE_KEY, "")
            }
        }
        return api.createPost(
            jsonRequestBody,
            imagesPart
        )
    }


    override suspend fun deletePost(postId: Int): Response<ResponseBody> =
        api.deletePosts(postId = postId)

    override suspend fun updatePost(
        postId: Int,
        title: String,
        content: String
    ): Response<ResponseBody> {
        return api.updatePost(
            postId = postId,
            CommunityRequestBody(title = title, content = content)
        )
    }

    override suspend fun clickLikeCommunity(postId: Int): Response<ResponseBody> =
        api.clickLikeCommunity(postId = postId)

    override suspend fun clickUnLikeCommunity(postId: Int): Response<ResponseBody> =
        api.clickUnLikeCommunity(postId = postId)

    override suspend fun getPopularCommunityPost(): Response<List<CommunityDto>> =
        api.getPopularCommunityPost()

}