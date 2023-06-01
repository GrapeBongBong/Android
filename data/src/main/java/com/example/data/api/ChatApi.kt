package com.example.data.api

import com.example.data.model.ResponseBody
import com.example.data.model.chat.ChatRoomDto
import com.example.data.model.chat.CreateChatRoomRequestBody
import com.example.data.model.chat.CreateChatRoomResponseBody
import com.example.data.model.chat.PostChatRoomDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {

    @POST("/chat")
    suspend fun createChatRoom(
        @Body createChatRoomRequestBody: CreateChatRoomRequestBody
    ): Response<CreateChatRoomResponseBody>

    @GET("/profile/chatRoom")
    suspend fun getAllMyChatRoom(): Response<List<ChatRoomDto>>

    @GET("/chat/rooms/{postId}")
    suspend fun getAllThisPostChatRoom(
        @Path("postId") postId: Int,
    ): Response<List<PostChatRoomDto>>

    @POST("/match/{postId}")
    suspend fun successMatching(
        @Path("postId") postId: Int,
    ): Response<ResponseBody>

}