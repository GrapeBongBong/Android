package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.source.CommentRemoteDataSource
import com.example.domain.model.comment.Comment
import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentRemoteDataSource: CommentRemoteDataSource
) : CommentRepository {

    override suspend fun getAll(postId: Int): Result<List<Comment>> {
        return try {
            val response = commentRemoteDataSource.getAll(postId = postId)
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                if (responseBody.contents != null) {
                    val data = responseBody.contents.map {
                        it.toEntity()
                    }
                    Result.success(data)
                } else {
                    Result.success(emptyList())
                }
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