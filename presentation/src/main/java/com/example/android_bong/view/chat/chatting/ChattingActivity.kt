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
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityChattingBinding
import com.example.android_bong.extension.setResultRefresh
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::updateUi)
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


    private fun initEvent() {

        binding.successButton.setOnClickListener {
            onClickSuccessMenu()
        }

        binding.editChatMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 문자열 변경 전에 호출됩니다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력 문자열이 변경될 때마다 호출됩니다.
                val inputText = s.toString()
                viewModel.updateMyChatMessage(inputText)
                Log.d("message", viewModel.uiState.value.myChatMessage.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // 입력 문자열 변경 후에 호출됩니다.
            }
        })
    }

    private fun updateUi(uiState: ChattingUiState) = with(binding) {

        if (getPostId() == 0) {
            successButton.isVisible = false
        }

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
            .setTitle("Rate Item")
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

}