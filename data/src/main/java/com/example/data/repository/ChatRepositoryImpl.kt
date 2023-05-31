package com.example.data.repository

import com.example.data.mapper.toEntity
import com.example.data.source.ChatRemoteDataSource
import com.example.domain.model.chat.ChatRoom
import com.example.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDataSource: ChatRemoteDataSource
) : ChatRepository {
    override suspend fun createChatRoom(
        exchangePostId: Int,
        applicantId: String
    ): Result<String> {
        return try {
            val response = chatRemoteDataSource.createChatRoom(
                exchangePostId = exchangePostId,
                applicantId = applicantId
            )
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                Result.success("채팅방이 생성되었습니다.")
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else if (response.code() == 404) {
                throw Exception("가입된 사용자가 아닙니다.")
            } else if (response.code() == 409) {
                throw Exception("이미 채팅방이 존재합니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다. {에러 메시지}")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllMyChatRoom(): Result<List<ChatRoom>> {
        return try {
            val response = chatRemoteDataSource.getAllMyChatRoom()
            val responseBody = response.body()
            if (responseBody != null && response.code() == 200) {
                val data = responseBody.map {
                    it.toEntity()
                }
                Result.success(data)
            } else if (response.code() == 401) {
                throw Exception("유효하지 않은 토큰입니다.")
            } else if (response.code() == 404) {
                throw Exception("가입된 사용자가 아닙니다.")
            } else {
                throw Exception("서버에 예기치 않은 오류가 발생했습니다.")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}