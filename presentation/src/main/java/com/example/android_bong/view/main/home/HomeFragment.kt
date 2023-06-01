package com.example.android_bong.view.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentHomeBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.extension.addDividerDecoration
import com.example.android_bong.view.main.MainViewModel
import com.example.android_bong.view.main.community.CommunityAdapter
import com.example.android_bong.view.main.community.CommunityItemUiState
import com.example.android_bong.view.main.community.detail.CommunityDetailActivity
import com.example.android_bong.view.main.profile.ProfileUiState
import com.example.android_bong.view.main.talentexchange.TalentExchangeAdapter
import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState
import com.example.android_bong.view.main.talentexchange.detail.TalentExchangeDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: MainViewModel by activityViewModels()

    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bind()

        val exchangeAdapter = TalentExchangeAdapter(::onClickExchangePost)
        val communityAdapter = CommunityAdapter(::onClickCommunityPost)

        initExchangeRecyclerView(exchangeAdapter)
        initCommunityRecyclerView(communityAdapter)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileUiState.collect {
                    updateProfileUi(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updatePostsUi(it, exchangeAdapter, communityAdapter)
                }
            }
        }

        launcher = registerForActivityResult(RefreshStateContract()) {
            if (it != null) {
                viewModel.bind()
                exchangeAdapter.submitList(viewModel.uiState.value.exchangePosts)
                communityAdapter.submitList(viewModel.uiState.value.communityPosts)
            }
        }
    }

    private fun initExchangeRecyclerView(adapter: TalentExchangeAdapter) = with(binding) {
        exchangePostRecyclerView.adapter = adapter
        exchangePostRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        exchangePostRecyclerView.addDividerDecoration()

    }

    private fun initCommunityRecyclerView(adapter: CommunityAdapter) = with(binding) {
        communityPostRecyclerView.adapter = adapter
        communityPostRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        communityPostRecyclerView.addDividerDecoration()
    }

    private fun updateProfileUi(uiState: ProfileUiState) = with(binding) {
        if (uiState.currentUser != null) {
            userNickname.text = getString(R.string.user_nickname, uiState.currentUser.nickName)
            userTemperature.text =
                getString(R.string.user_temperature, uiState.currentUser.temperature.toString())
        }

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage.toString())
            viewModel.userProfileMessageShown()
        }
    }

    private fun updatePostsUi(
        uiState: HomeUiState,
        exchangeAdapter: TalentExchangeAdapter,
        communityAdapter: CommunityAdapter
    ) = with(binding) {


        exchangeAdapter.submitList(uiState.exchangePosts)
        communityAdapter.submitList(uiState.communityPosts)
        if (uiState.userMessage != null) {
            Log.d("error", uiState.userMessage)
            viewModel.userMessageShown()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun onClickExchangePost(talentExchangeItemUiState: TalentExchangeItemUiState) {
        val intent =
            TalentExchangeDetailActivity.getIntent(requireContext(), talentExchangeItemUiState.pid)
        launcher?.launch(intent)
    }

    private fun onClickCommunityPost(communityItemUiState: CommunityItemUiState) {
        val intent = CommunityDetailActivity.getIntent(requireContext(), communityItemUiState.pid)
        launcher?.launch(intent)
    }

}