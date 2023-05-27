package com.example.android_bong.view.main.community.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityDetailBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.extension.addDividerDecoration
import com.example.android_bong.extension.setResultRefresh
import com.example.android_bong.view.main.comment.CommentAdapter
import com.example.android_bong.view.main.comment.CommentItemUiState
import com.example.android_bong.view.main.comment.CommentUiState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityDetailActivity : ViewBindingActivity<ActivityCommunityDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommunityDetailBinding
        get() = ActivityCommunityDetailBinding::inflate


    companion object {
        fun getIntent(context: Context, postId: Int): Intent {
            return Intent(context, CommunityDetailActivity::class.java).apply {
                putExtra("postId", postId)
            }
        }
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }

    private var launcher: ActivityResultLauncher<Intent>? = null

    private val viewModel: CommunityDetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        val postId = getPostId()
        viewModel.bind(postId)

        val adapter = CommentAdapter(::onClickCommentMenu)
        initRecyclerView(adapter)
        initEvent()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.communityDetailUiState.collect {
                    updatePostDetailUi(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.commentUiState.collect {
                    updateCommentsUi(it, adapter)
                }
            }
        }

        launcher = registerForActivityResult(RefreshStateContract()) {
            if (it != null) {
                viewModel.bind(postId)
                it.message?.let { message -> showSnackBar(message) }
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

    private fun initEvent() = with(binding) {
        postDetailButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(applicationContext, view)
            menuInflater.inflate(R.menu.menu_post_detail, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit_post -> {
                        onClickUpdatePostMenu(viewModel.communityDetailUiState.value)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_delete_post -> {
                        onClickDeletePostMenu(viewModel.communityDetailUiState.value)
                        return@setOnMenuItemClickListener true
                    }

                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }

        createCommentButton.setOnClickListener {
            viewModel.createComment()
        }

        comment.addTextChangedListener {
            if (it != null) {
                viewModel.updateCommentContent(it.toString())
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initRecyclerView(adapter: CommentAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@CommunityDetailActivity)
        recyclerView.addDividerDecoration()
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

                postDetailButton.isVisible = postDetail.isMine
            }

            if (uiState.postDeletingSuccess) {
                setResultRefresh()
                finish()
            }

            if (uiState.userMessage != null) {
                showSnackBar(uiState.userMessage)
                viewModel.postDetailUserMessageShown()
            }
        }

    private fun updateCommentsUi(uiState: CommentUiState, adapter: CommentAdapter) = with(binding) {
        binding.loadState.emptyText.isVisible =
            uiState.comments.isEmpty()

        loadState.retryButton.isVisible = !uiState.isLoadingSuccess
        loadState.errorMsg.isVisible = !uiState.isLoadingSuccess

        loadState.progressBar.isVisible = uiState.isLoading
        recyclerView.isVisible = !uiState.isLoading
        adapter.submitList(uiState.comments)

        createCommentButton.apply {
            isEnabled = uiState.isValidComment
        }

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            viewModel.commentUserMessageShown()
        }
    }

    private fun onClickCommentMenu(uiState: CommentItemUiState) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(getString(R.string.delete_comment))
            setMessage(R.string.are_you_sure_you_want_to_delete)
            setNegativeButton(R.string.cancel) { _, _ -> }
            setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteComment(uiState = uiState)
            }
        }.show()
    }

    private fun onClickDeletePostMenu(uiState: CommunityDetailUiState) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(getString(R.string.delete_post))
            setMessage(R.string.are_you_sure_you_want_to_delete)
            setNegativeButton(R.string.cancel) { _, _ -> }
            setPositiveButton(R.string.delete) { _, _ ->
                viewModel
            }
        }.show()
    }

    private fun onClickUpdatePostMenu(uiState: CommunityDetailUiState) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(getString(R.string.update_post))
            setMessage(R.string.are_you_sure_you_want_to_update)
            setNegativeButton(R.string.cancel) { _, _ -> }
            setPositiveButton(R.string.update) { _, _ ->
                navigateToEditActivity()
            }
        }.show()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToEditActivity() {

    }


}