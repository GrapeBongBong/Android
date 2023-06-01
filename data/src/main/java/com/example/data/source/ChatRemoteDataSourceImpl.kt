package com.example.data.source

import com.example.data.api.ChatApi
import com.example.data.model.ResponseBody
import com.example.data.model.chat.ChatRoomDto
import com.example.data.model.chat.CreateChatRoomRequestBody
import com.example.data.model.chat.CreateChatRoomResponseBody
import com.example.data.model.chat.PostChatRoomDto
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

    override suspend fun getAllThisPostChatRoom(postId: Int): Response<List<PostChatRoomDto>> =
        api.getAllThisPostChatRoom(postId = postId)

    override suspend fun successMatching(postId: Int): Response<ResponseBody> =
        api.successMatching(postId = postId)

}