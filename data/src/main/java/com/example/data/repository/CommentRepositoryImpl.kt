package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.model.comment.CreateCommentRequestBody
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

                val data = responseBody.contents.map {
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

    override suspend fun createComment(postId: Int, content: String): Result<Unit> {
        return try {
            val response = commentRemoteDataSource.createComment(
                postId = postId,
                createCommentRequestBody = CreateCommentRequestBody(content = content)
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(Unit)
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else if (response.code() == 403) {
                throw Exception("필수 정보가 없습니다.")
            } else if (response.code() == 404) {
                throw Exception("로그인이 필요합니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteComment(postId: Int, commentId: Int): Result<Unit> {
        return try {
            val response = commentRemoteDataSource.deleteComment(
                postId = postId,
                commentId = commentId
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(Unit)
            } else if (response.code() == 401) {
                throw Exception("해당 댓글의 작성자가 아닙니다.")
            } else if (response.code() == 403) {
                throw Exception("필수 정보가 없습니다.")
            } else if (response.code() == 404) {
                throw Exception("존재하지 않는 댓글입니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateComment(postId: Int, commentId: Int, content: String): Result<Unit> {
        return try {
            val response = commentRemoteDataSource.createComment(
                postId = postId,
                createCommentRequestBody = CreateCommentRequestBody(content = content)
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success(Unit)
            } else if (response.code() == 401) {
                throw Exception("해당 댓글을 수정할 권한이 없습니다.")
            } else if (response.code() == 403) {
                throw Exception("필수 정보가 없습니다.")
            } else if (response.code() == 404) {
                throw Exception("로그인이 필요합니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}