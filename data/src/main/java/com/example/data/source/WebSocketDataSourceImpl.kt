package com.example.data.source

import com.example.data.service.WebSocketService
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocketDataSourceImpl @Inject constructor(
    private val webSocketService: WebSocketService
) : WebSocketDataSource {

    override fun connect() {

    }

    override fun disconnect() {

    }

    override fun sendMessage(message: String) {

    }

    override fun setListener(listener: WebSocketListener) {

    }
}