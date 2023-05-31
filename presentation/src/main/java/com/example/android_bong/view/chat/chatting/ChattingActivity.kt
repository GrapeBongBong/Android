package com.example.android_bong.view.chat.chatting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityChattingBinding

class ChattingActivity : ViewBindingActivity<ActivityChattingBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityChattingBinding
        get() = ActivityChattingBinding::inflate

    companion object {
        fun getIntent(context: Context, roomId: Int): Intent {
            return Intent(context, ChattingActivity::class.java).apply {
                putExtra("postId", roomId)
            }
        }
    }

    private fun getRoomId(): Int {
        return intent.getIntExtra("roomId", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val roomId = getRoomId()
    }
}