package com.example.android_bong.view.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityChattingRoomBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.extension.addDividerDecoration
import com.example.android_bong.extension.setResultRefresh
import com.example.android_bong.view.chat.chatting.ChattingActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChattingRoomActivity : ViewBindingActivity<ActivityChattingRoomBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityChattingRoomBinding
        get() = ActivityChattingRoomBinding::inflate


    private val viewModel: ChattingRoomViewModel by viewModels()

    companion object {
        fun getIntent(context: Context, postId: Int): Intent {
            return Intent(context, ChattingRoomActivity::class.java).apply {
                putExtra("postId", postId)
            }
        }
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.checkThisPostChatRoom)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        val postId = getPostId()
        viewModel.bind(postId)

        val adapter = ChattingRoomAdapter(::onClickChatRoom)
        initRecyclerView(adapter)
        initEventListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it, adapter)
                }
            }
        }

        launcher = registerForActivityResult(RefreshStateContract()) {
            if (it != null) {
                viewModel.bind(postId)
                adapter.submitList(viewModel.uiState.value.rooms)
                it.message?.let { message -> showSnackBar(message) }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        setResultRefresh()
        return super.onKeyDown(keyCode, event)
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

    private var launcher: ActivityResultLauncher<Intent>? = null

    private fun updateUi(uiState: ChattingRoomUiSate, adapter: ChattingRoomAdapter) =
        with(binding) {
            binding.loadState.emptyText.isVisible =
                uiState.rooms.isEmpty()

            loadState.retryButton.isVisible = !uiState.isLoadingSuccess
            loadState.errorMsg.isVisible = !uiState.isLoadingSuccess

            loadState.progressBar.isVisible = uiState.isLoading
            recyclerView.isVisible = !uiState.isLoading

            adapter.submitList(uiState.rooms)

            if (uiState.userMessage != null) {
                showSnackBar(uiState.userMessage)
                Log.d("error", uiState.userMessage)
                viewModel.userMessageShown()
            }

        }

    private fun initRecyclerView(adapter: ChattingRoomAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@ChattingRoomActivity)
        recyclerView.addDividerDecoration()

    }

    private fun initEventListeners() {
        binding.loadState.retryButton.setOnClickListener {

        }
    }

    private fun onClickChatRoom(postChatRoomItemUiState: PostChatRoomItemUiState) {
        val intent = ChattingActivity.getIntent(
            this,
            roomId = postChatRoomItemUiState.roomId,
            postId = postChatRoomItemUiState.pid,
            roomTitle = postChatRoomItemUiState.roomName
        )
        launcher?.launch(intent)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}