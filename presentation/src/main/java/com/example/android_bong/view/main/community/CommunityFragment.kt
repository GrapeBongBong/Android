package com.example.android_bong.view.main.community

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentCommunityBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.extension.addDividerDecoration
import com.example.android_bong.view.main.community.create.CommunityCreateActivity
import com.example.android_bong.view.main.community.detail.CommunityDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CommunityFragment : ViewBindingFragment<FragmentCommunityBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCommunityBinding
        get() = FragmentCommunityBinding::inflate

    private val viewModel: CommunityViewModel by activityViewModels()

    private var launcher: ActivityResultLauncher<Intent>? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPosts()
        val adapter = CommunityAdapter(::onClickPost)
        initRecyclerView(adapter)
        initEventListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it, adapter)
                }
            }
        }

        launcher = registerForActivityResult(RefreshStateContract()) {
            if (it != null) {
                viewModel.fetchPosts()
                adapter.submitList(viewModel.uiState.value.posts)
                it.message?.let { message -> showSnackBar(message) }
            }
        }
    }

    private fun updateUi(uiState: CommunityUiState, adapter: CommunityAdapter) = with(binding) {
        binding.loadState.emptyText.isVisible =
            uiState.posts.isEmpty()

        loadState.retryButton.isVisible = !uiState.isLoadingSuccess
        loadState.errorMsg.isVisible = !uiState.isLoadingSuccess

        loadState.progressBar.isVisible = uiState.isLoading
        recyclerView.isVisible = !uiState.isLoading

        adapter.submitList(uiState.posts)

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            Log.d("error", uiState.userMessage)
            viewModel.userMessageShown()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initRecyclerView(adapter: CommunityAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addDividerDecoration()
        recyclerView.setOnScrollChangeListener(::onScrollChangeListener)

    }

    private fun initEventListeners() {

        binding.fab.setOnClickListener {
            navigateToCreate()
        }

        binding.loadState.retryButton.setOnClickListener {
            viewModel.fetchPosts()
        }

    }

    private fun navigateToCreate() {
        val intent = CommunityCreateActivity.getIntent(requireContext())
        launcher?.launch(intent)
    }

    private fun onClickPost(communityItemUiState: CommunityItemUiState) {
        val intent = CommunityDetailActivity.getIntent(requireContext(), communityItemUiState.pid)
        launcher?.launch(intent)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onScrollChangeListener(
        v: View,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (scrollY > oldScrollY) {
            binding.fab.shrink()
        } else {
            binding.fab.extend()
        }
    }
}