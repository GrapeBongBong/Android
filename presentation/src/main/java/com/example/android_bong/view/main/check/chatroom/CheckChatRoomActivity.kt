package com.example.android_bong.view.main.check.chatroom

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
import com.example.android_bong.databinding.ActivityCheckChatRoomBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.extension.addDividerDecoration
import com.example.android_bong.extension.setResultRefresh
import com.example.android_bong.view.chat.chatting.ChattingActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckChatRoomActivity : ViewBindingActivity<ActivityCheckChatRoomBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCheckChatRoomBinding
        get() = ActivityCheckChatRoomBinding::inflate

    private val viewModel: CheckChatRoomViewModel by viewModels()

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CheckChatRoomActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.checkMyChatRoom)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        viewModel.fetchRooms()
        val adapter = CheckChatRoomAdapter(::onClickChatRoom)
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
                viewModel.fetchRooms()
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

    private fun updateUi(uiState: CheckChatRoomUiState, adapter: CheckChatRoomAdapter) =
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

    private fun initRecyclerView(adapter: CheckChatRoomAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@CheckChatRoomActivity)
        recyclerView.addDividerDecoration()

    }

    private fun initEventListeners() {
        binding.loadState.retryButton.setOnClickListener {
            viewModel.fetchRooms()
        }
    }

    private fun onClickChatRoom(chatRoomItemUiState: ChatRoomItemUiState) {
        val intent = ChattingActivity.getIntent(
            this,
            roomId = chatRoomItemUiState.roomId,
            postId = 0,
            roomTitle = chatRoomItemUiState.roomName
        )
        launcher?.launch(intent)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}