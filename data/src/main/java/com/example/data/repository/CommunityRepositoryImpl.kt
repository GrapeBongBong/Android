package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.source.CommunityRemoteDataSource
import com.example.domain.model.community.CommunityPost
import com.example.domain.repository.CommunityRepository
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
        TODO("Not yet implemented")
    }

    override suspend fun createPost(title: String, content: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(postId: Int): Result<String> {
        TODO("Not yet implemented")
    }

}