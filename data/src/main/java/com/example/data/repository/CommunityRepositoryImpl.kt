package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.source.CommunityRemoteDataSource
import com.example.domain.model.community.CommunityPost
import com.example.domain.repository.CommunityRepository
import java.io.File
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityRemoteDataSource: CommunityRemoteDataSource
) : CommunityRepository {

    override suspend fun getAllPost(): Result<List<CommunityPost>> {
        return try {
            val response = communityRemoteDataSource.getAll()
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                val data = responseBody.map {
                    it.toEntity()
                }
                Result.success(data)
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePost(postId: Int): Result<String> {
        return try {
            val response = communityRemoteDataSource.deletePost(postId = postId)
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(responseBody.message)
            } else {
                throw Exception(responseBody!!.message)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createPost(
        title: String,
        content: String,
        images: List<File?>
    ): Result<String> {
        return try {
            val response = communityRemoteDataSource.createPost(
                title = title,
                content = content,
                images = images
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 201) {
                Result.success(responseBody.message)
            } else {
                throw Exception(responseBody!!.message)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePost(postId: Int, title: String, content: String): Result<String> {
        return try {
            val response = communityRemoteDataSource.updatePost(
                postId = postId,
                title = title,
                content = content
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(responseBody.message)
            } else {
                throw Exception(responseBody!!.message)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clickLike(postId: Int): Result<String> {
        return try {
            val response = communityRemoteDataSource.clickLikeCommunity(
                postId = postId
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(responseBody.message)
            } else {
                throw Exception(responseBody!!.message)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clickUnLike(postId: Int): Result<String> {
        return try {
            val response = communityRemoteDataSource.clickUnLikeCommunity(
                postId = postId
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(responseBody.message)
            } else {
                throw Exception(responseBody!!.message)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getPopularCommunityPost(): Result<List<CommunityPost>> {
        return try {
            val response = communityRemoteDataSource.getPopularCommunityPost()
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                val data = responseBody.map {
                    it.toEntity()
                }
                Result.success(data)
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}