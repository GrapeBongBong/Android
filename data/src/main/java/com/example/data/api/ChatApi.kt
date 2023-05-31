package com.example.data.api

import com.example.data.model.chat.ChatRoomDto
import com.example.data.model.chat.CreateChatRoomRequestBody
import com.example.data.model.chat.CreateChatRoomResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatApi {

    @POST("/chat")
    suspend fun createChatRoom(
        @Body createChatRoomRequestBody: CreateChatRoomRequestBody
    ): Response<CreateChatRoomResponseBody>

    @GET("/profile/chatRoom")
    suspend fun getAllMyChatRoom(): Response<List<ChatRoomDto>>

}