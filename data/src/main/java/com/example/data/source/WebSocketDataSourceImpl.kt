package com.example.data.source

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import javax.inject.Inject

class WebSocketDataSourceImpl @Inject constructor(
    private val client: OkHttpClient
) : WebSocketDataSource {

    private var webSocket: WebSocket? = null

    override fun connect(url: String, listener: WebSocketListener) {
        val request = Request.Builder()
            .url(url)
            .build()
        webSocket = client.newWebSocket(request, listener)
    }

    override fun disconnect() {
        Log.d("disconnect", "disconnect")
        webSocket?.cancel()
        webSocket = null
    }

    override fun sendMessage(roomId: Int, senderId: String, message: String) {
        val messageJson = JSONObject().apply {
            put("roomId", roomId)
            put("senderId", senderId)
            put("message", message)
        }

        if (webSocket?.send(messageJson.toString()) == true) {
            Log.d("message success", messageJson.toString())
        } else {
            Log.d("message false", messageJson.toString())
        }
    }
}