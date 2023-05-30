package com.example.data.source

import com.example.data.api.UserApi
import com.example.data.model.ResponseBody
import com.example.data.model.community.CommunityDto
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.data.model.profile.ProfileUpdateRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val api: UserApi
) : UserRemoteDataSource {

    companion object {
        private const val IMAGE_KEY = "image"
    }

    override suspend fun updateUserInfo(
        userId: Int,
        profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<ResponseBody> = api.updateUserInfo(
        userId = userId,
        profileUpdateRequestBody = profileUpdateRequestBody
    )

    override suspend fun updateUserProfileImage(
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
        return api.updateUserProfileImage(
            image = image
        )
    }

    override suspend fun myCommunityPost(): Response<List<CommunityDto>> = api.myCommunityPost()


    override suspend fun myExchangePost(): Response<List<ExchangePostDto>> = api.myExchangePost()
}