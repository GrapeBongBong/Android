package com.example.data.repository

import com.example.data.source.WebSocketDataSource
import com.example.domain.repository.ChattingRepository
import okhttp3.WebSocketListener
import javax.inject.Inject

class ChattingRepositoryImpl @Inject constructor(
    private val webSocketDataSource: WebSocketDataSource
) : ChattingRepository {

    override fun connect(url: String, listener: WebSocketListener) =
        webSocketDataSource.connect(url = url, listener = listener)

    override fun disconnect() = webSocketDataSource.disconnect()

    override fun sendMessage(roomId: Int, senderId: String, message: String) =
        webSocketDataSource.sendMessage(roomId = roomId, senderId = senderId, message = message)
}