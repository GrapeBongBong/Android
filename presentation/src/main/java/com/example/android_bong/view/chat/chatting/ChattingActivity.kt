package com.example.android_bong.view.chat.chatting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityChattingBinding
import com.example.android_bong.extension.setResultRefresh

class ChattingActivity : ViewBindingActivity<ActivityChattingBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityChattingBinding
        get() = ActivityChattingBinding::inflate

    companion object {
        fun getIntent(context: Context, roomId: Int, postId: Int?, roomTitle: String): Intent {
            return Intent(context, ChattingActivity::class.java).apply {
                putExtra("roomId", roomId)
                putExtra("postId", postId)
                putExtra("roomTitle", roomTitle)
            }
        }
    }

    private fun getRoomId(): Int {
        return intent.getIntExtra("roomId", 0)
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }

    private fun getRoomTitle(): String {
        return intent.getStringExtra("title")!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.title = getRoomTitle()
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        val roomId = getRoomId()

        /**
         * 얘는 null 일수도 있음
         */
        val postId = getPostId()
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
}