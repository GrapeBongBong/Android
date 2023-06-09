package com.example.data.source

import com.example.data.model.ResponseBody
import com.example.data.model.chat.ChatRoomDto
import com.example.data.model.chat.CreateChatRoomResponseBody
import com.example.data.model.chat.PostChatRoomDto
import retrofit2.Response

interface ChatRemoteDataSource {

    suspend fun createChatRoom(
        exchangePostId: Int,
        applicantId: String
    ): Response<CreateChatRoomResponseBody>

    suspend fun getAllMyChatRoom(): Response<List<ChatRoomDto>>

    suspend fun getAllThisPostChatRoom(postId: Int): Response<List<PostChatRoomDto>>

    suspend fun successMatching(postId: Int): Response<ResponseBody>

    suspend fun applyScore(postId: Int, score: Int): Response<ResponseBody>

}