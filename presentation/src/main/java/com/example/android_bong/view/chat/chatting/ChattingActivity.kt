package com.example.android_bong.view.chat.chatting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.RatingBar
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityChattingBinding
import com.example.android_bong.extension.setResultRefresh
import com.example.domain.model.chat.ChatMessage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class ChattingActivity : ViewBindingActivity<ActivityChattingBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityChattingBinding
        get() = ActivityChattingBinding::inflate

    companion object {
        fun getIntent(context: Context, roomId: Int, postId: Int, roomTitle: String): Intent {
            return Intent(context, ChattingActivity::class.java).apply {
                putExtra("roomId", roomId)
                putExtra("postId", postId)
                putExtra("roomTitle", roomTitle)
            }
        }
    }

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Log.d("onOpen", response.code.toString())
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            // 서버에서 텍스트 메시지를 받았을 때 처리하는 로직, 예: 기본 메시지 또는 이전 채팅 메시지 받기
            try {
                val jsonArray = JSONArray(text)
                val chatMessages = ArrayList<ChatMessage>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    chatMessages.add(
                        ChatMessage(
                            roomId = jsonObject.getInt("roomId"),
                            senderId = jsonObject.getString("senderId"),
                            message = jsonObject.getString("message"),
                            messageId = i
                        )
                    )
                }
                viewModel.bindChatting(chatMessages)
            } catch (e: Exception) {  // 일반 채팅 메시지 처리

            }

        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Log.d("onClosed", code.toString())
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Log.d("onMessage", bytes.toString())
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.d("onFailure", response.toString())
        }
        // 오류 발생시 처리
    }


    private fun connect(chatRoomId: Int) {
        val request = Request.Builder()
            .url("ws://3.34.75.23:8080/ws/chat/${chatRoomId}")
            .build()
        webSocket = client.newWebSocket(request, listener)
    }

    private fun disconnect() {
        Log.d("disconnect", "disconnect")
        webSocket?.close(1000, null)
    }

    private fun sendMessage(roomId: Int, senderId: String, message: String) {
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


    private fun getRoomId(): Int {
        return intent.getIntExtra("roomId", 0)
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }

    private fun getRoomTitle(): String {
        return intent.getStringExtra("roomTitle")!!
    }

    private val viewModel: ChattingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postId = getPostId()
        val roomId = getRoomId()
        val roomTitle = getRoomTitle()
        viewModel.bind(
            postId = postId,
            roomId = roomId,
            roomTitle = roomTitle
        )
        connect(roomId)
        binding.toolbar.title = getRoomTitle()
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        val adapter = ChattingAdapter(viewModel.uiState.value.senderId)
        initEvent()
        initRecyclerView(adapter)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it, adapter)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResultRefresh()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        setResultRefresh()
        return super.onKeyDown(keyCode, event)
    }

    private fun initRecyclerView(adapter: ChattingAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@ChattingActivity).apply {
            this.stackFromEnd = true    // 가장 최근의 대화를 표시하기 위해 맨 아래로 정렬.
        }
    }

    private fun initEvent() = with(binding) {
        binding.successButton.setOnClickListener {
            onClickSuccessMenu()
        }
        binding.buttonGchatSend.setOnClickListener {
            sendMessage(
                roomId = getRoomId(),
                senderId = viewModel.uiState.value.senderId,
                message = viewModel.uiState.value.myChatMessage
            )
        }

        binding.editChatMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 문자열 변경 전에 호출됩니다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력 문자열이 변경될 때마다 호출됩니다.
                val inputText = s.toString()
                viewModel.updateMyChatMessage(inputText)
            }

            override fun afterTextChanged(s: Editable?) {
                // 입력 문자열 변경 후에 호출됩니다.
            }
        })
    }

    private fun updateUi(uiState: ChattingUiState, adapter: ChattingAdapter) = with(binding) {
        if (getPostId() == 0) {
            successButton.isVisible = false
        }

        adapter.submitList(uiState.chatting)

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            Log.d("error", uiState.userMessage)
            viewModel.userMessageShown()
        }
    }

    private fun onClickSuccessMenu() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(getString(R.string.matching_success))
            setMessage(R.string.are_you_sure_you_want_to_success)
            setNegativeButton(R.string.cancel) { _, _ -> }
            setPositiveButton(R.string.match_successed) { _, _ ->
                viewModel.clickSuccess()
                onClickScoreMenu()
            }
        }.show()
    }

    private fun onClickScoreMenu() {
        showRatingDialog(this@ChattingActivity)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showRatingDialog(context: Context) {
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.rating_dialog_layout, null)
        val ratingBar: RatingBar = dialogView.findViewById(R.id.rating_bar)

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton(R.string.Submit) { _, _ ->
                val rating = ratingBar.rating.toInt()
                viewModel.updateScore(rating)
                viewModel.applyScore()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
    }
}