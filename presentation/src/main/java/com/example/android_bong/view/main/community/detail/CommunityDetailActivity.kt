package com.example.android_bong.view.main.community.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.R
import com.example.android_bong.common.GlideApp
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityDetailBinding
import com.example.android_bong.extension.*
import com.example.android_bong.view.main.comment.CommentAdapter
import com.example.android_bong.view.main.comment.CommentItemUiState
import com.example.android_bong.view.main.comment.CommentUiState
import com.example.android_bong.view.main.community.edit.CommunityEditActivity
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
            hideKeyboard()
            viewModel.createComment()
            comment.setText("")
        }

        comment.addTextChangedListener {
            if (it != null) {
                viewModel.updateCommentContent(it.toString())
            }
        }

        likeImage.setOnClickListener {
            viewModel.clickLike()
        }
    }


    private fun initRecyclerView(adapter: CommentAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@CommunityDetailActivity)
        recyclerView.addDividerDecoration()
    }


    private fun updatePostDetailUi(uiState: CommunityDetailUiState) =
        with(binding) {
            val postDetail = uiState.postDetail
            val glide = GlideApp.with(this@CommunityDetailActivity)

            if (postDetail != null) {
                title.text = postDetail.title
                content.text = postDetail.content
                if (uiState.postDetail.isMine) {
                    nickName.text = root.context.getString(
                        R.string.isMinePost
                    )
                }
                date.text = convertDateTimeFormat(postDetail.date)

                postDetailButton.isVisible = postDetail.isMine

                if (uiState.postDetail.liked) {
                    glide.load(R.drawable.leaf_fill)
                        .into(likeImage)
                } else {
                    glide.load(R.drawable.leaf_border)
                        .into(likeImage)
                }
                val imageViews = listOf(imageView1, imageView2, imageView3)
                if (postDetail.images!!.isEmpty()) {
                    imageLinearLayout.isVisible = false
                } else {
                    for (i in postDetail.images.indices) {
                        if (i < imageViews.size) {
                            val imageView = imageViews[i]
                            val imageURL = postDetail.images[i].fileUrl
                            glide.load(imageURL)
                                .override(200, 200)
                                .into(imageView)
                            imageView.isVisible = true
                        }
                    }
                    for (i in postDetail.images.size until imageViews.size) {
                        val imageView = imageViews[i]
                        imageView.isVisible = false
                    }
                }

                likedCount.text = uiState.postDetail.likeCount.toString()
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
                viewModel.deletePost(uiState.postId!!)
            }
        }.show()
    }

    private fun onClickUpdatePostMenu(uiState: CommunityDetailUiState) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(getString(R.string.update_post))
            setMessage(R.string.are_you_sure_you_want_to_update)
            setNegativeButton(R.string.cancel) { _, _ -> }
            setPositiveButton(R.string.update) { _, _ ->
                navigateToEditActivity(uiState)
            }
        }.show()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToEditActivity(uiState: CommunityDetailUiState) {
        val intent = CommunityEditActivity.getIntent(
            context = this,
            postId = uiState.postId!!,
            title = uiState.postDetail!!.title,
            content = uiState.postDetail.content
        )
        launcher?.launch(intent)
    }


}