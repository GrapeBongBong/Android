package com.example.data.source

import okhttp3.WebSocketListener

interface WebSocketDataSource {
    fun connect(url: String, listener: WebSocketListener)
    fun disconnect()
    fun sendMessage(roomId: Int, senderId: String, message: String)
}