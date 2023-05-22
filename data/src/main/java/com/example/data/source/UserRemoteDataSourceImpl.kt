package com.example.data.source

import com.example.data.api.UserApi
import com.example.data.model.profile.ProfileUpdateRequestBody
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val api: UserApi,
    private val localDataSource: AuthLocalDataSource
) : UserRemoteDataSource {
    override suspend fun updateUserInfo(
        userId: Int,
        profileUpdateRequestBody: ProfileUpdateRequestBody
    ): Response<Unit> = api.updateUserInfo(
        token = localDataSource.getData()!!.sessionToken,
        userId = userId,
        profileUpdateRequestBody = profileUpdateRequestBody
    )
}