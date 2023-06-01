package com.example.domain.repository

import okhttp3.WebSocketListener

interface ChattingRepository {
    fun connect(url: String, listener: WebSocketListener)
    fun disconnect()
    fun sendMessage(roomId: Int, senderId: String, message: String)
}