package com.example.android_bong.view.main.community.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityEditBinding
import com.example.android_bong.extension.setResultRefresh
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityEditActivity :
    ViewBindingActivity<ActivityCommunityEditBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommunityEditBinding
        get() = ActivityCommunityEditBinding::inflate

    companion object {
        fun getIntent(
            context: Context, postId: Int, title: String, content: String
        ): Intent {
            return Intent(context, CommunityEditActivity::class.java).apply {
                putExtra("postId", postId)
                putExtra("title", title)
                putExtra("content", content)
            }
        }
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }

    private fun getPostTitle(): String {
        return intent.getStringExtra("title")!!
    }

    private fun getPostContent(): String {
        return intent.getStringExtra("content")!!
    }

    private val viewModel: CommunityEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.community_updating)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        viewModel.bind(getPostId())

        initEvent()
        initSetting(title = getPostTitle(), content = getPostContent())

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
                setResultRefresh()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSetting(title: String, content: String) = with(binding) {
        binding.title.setText(title)
        binding.content.setText(content)
        viewModel.updateContent(title)
        viewModel.updateContent(content)

    }

    private fun updateUi(uiState: CommunityEditUiState) = with(binding) {

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            viewModel.userMessageShown()
        }

        if (uiState.isSuccessUpdating) {
            setResultRefresh()
            finish()
        }

        updateButton.apply {
            isEnabled = uiState.isInputValid && !uiState.isLoading
            setText(if (uiState.isLoading) R.string.loading else R.string.doing_update)
        }
    }

    private fun initEvent() = with(binding) {

        title.addTextChangedListener {
            if (it != null) {
                viewModel.updateTitle(it.toString())
            }
        }

        content.addTextChangedListener {
            if (it != null) {
                viewModel.updateContent(it.toString())
            }
        }

        updateButton.setOnClickListener {
            viewModel.updatePost()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}