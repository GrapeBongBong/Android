package com.example.data.source

import okhttp3.WebSocketListener

interface WebSocketDataSource {
    fun connect()
    fun disconnect()
    fun sendMessage(message: String)
    fun setListener(listener: WebSocketListener)
}
