package com.example.android_bong.view.main.community.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityDetailBinding
import com.example.android_bong.view.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityDetailActivity : ViewBindingActivity<ActivityCommunityDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommunityDetailBinding
        get() = ActivityCommunityDetailBinding::inflate


    companion object {
        fun getIntent(context: Context, postId: Int): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra("postId", postId)
            }
        }
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }

    private val viewModel: CommunityDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        val postId = getPostId()
        viewModel.bind(postId)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.communityDetailUiState.collect {
                    updatePostDetailUi(it)
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

    private fun updatePostDetailUi(uiState: CommunityDetailUiState) =
        with(binding) {
            val postDetail = uiState.postDetail

            if (postDetail != null) {
                title.text = postDetail.title
                content.text = postDetail.content
                if (uiState.postDetail.isMine) {
                    nickName.text = root.context.getString(
                        R.string.isMinePost
                    )
                }
                date.text = postDetail.date

                //postDetailButton.isVisible = postDetail.isNotMine
            }

            if (uiState.userMessage != null) {
                showSnackBar(uiState.userMessage)
                viewModel.postDetailUserMessageShown()
            }
        }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}