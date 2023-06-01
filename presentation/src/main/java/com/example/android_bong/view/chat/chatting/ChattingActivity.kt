package com.example.android_bong.view.chat.chatting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityChattingBinding
import com.example.android_bong.extension.setResultRefresh
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

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
        binding.toolbar.title = getRoomTitle()
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        initEvent()
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


    private fun initEvent() {

        binding.successButton.setOnClickListener {
            viewModel.clickSuccess()
        }

    }

    private fun updateUi(uiState: ChattingUiState) =
        with(binding) {


            if (uiState.userMessage != null) {
                showSnackBar(uiState.userMessage)
                Log.d("error", uiState.userMessage)
                viewModel.userMessageShown()
            }
        }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}