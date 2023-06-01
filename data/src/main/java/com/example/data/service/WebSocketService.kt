package com.example.data.service

import okhttp3.WebSocketListener

interface WebSocketService {

    suspend fun connectWebSocket()
    suspend fun disconnectWebSocket()
    suspend fun sendWebSocketMessage(message: String)
    suspend fun setWebSocketListener(listener: WebSocketListener)

}