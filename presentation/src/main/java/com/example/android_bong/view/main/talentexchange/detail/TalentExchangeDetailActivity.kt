package com.example.android_bong.view.main.talentexchange.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityTalentExchangeDetailBinding
import com.example.android_bong.view.main.talentexchange.TalentExchangeUiState
import com.example.android_bong.view.main.talentexchange.TalentExchangeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TalentExchangeDetailActivity :
    ViewBindingActivity<ActivityTalentExchangeDetailBinding>() {


    override val bindingInflater: (LayoutInflater) -> ActivityTalentExchangeDetailBinding
        get() = ActivityTalentExchangeDetailBinding::inflate

    companion object {
        fun getIntent(context: Context, postId: Int): Intent {
            return Intent(context, TalentExchangeDetailActivity::class.java).apply {
                putExtra("postId", postId)
            }
        }
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }

    private val viewModel: TalentExchangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        val postId = getPostId()
        Log.d("postId", postId.toString())
        viewModel.postDetailBind(postId)

        //TODO : 댓글 adapter와 viewHolder 필요

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUi(uiState: TalentExchangeUiState) = with(binding) {
        val postDetail = uiState.postDetail
        Log.d("postDetail", postDetail.toString())
        if (postDetail != null) {
            title.text = postDetail.title
            content.text = postDetail.content
            nickName.text =
                getString(R.string.post_id_nickName, postDetail.writerNick, postDetail.writerId)
            date.text = postDetail.date
            takeTalent.text = getString(R.string.take_text, postDetail.takeTalent)
            giveTalent.text = getString(R.string.give_text, postDetail.giveTalent)
        }

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            viewModel.userMessageShown()
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}