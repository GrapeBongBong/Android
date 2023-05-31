package com.example.data.source

import com.example.data.api.ChatApi
import com.example.data.model.chat.ChatRoomDto
import com.example.data.model.chat.CreateChatRoomRequestBody
import com.example.data.model.chat.CreateChatRoomResponseBody
import retrofit2.Response
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(
    private val api: ChatApi
) : ChatRemoteDataSource {
    override suspend fun createChatRoom(
        exchangePostId: Int,
        applicantId: String
    ): Response<CreateChatRoomResponseBody> {
        return api.createChatRoom(
            CreateChatRoomRequestBody(
                exchangePostId = exchangePostId,
                applicantId = applicantId
            )
        )
    }

    override suspend fun getAllMyChatRoom(): Response<List<ChatRoomDto>> = api.getAllMyChatRoom()

}