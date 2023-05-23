package com.example.android_bong.view.main.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentCommunityBinding
import com.example.android_bong.view.main.community.create.CommunityCreateActivity
import com.example.android_bong.view.main.community.detail.CommunityDetailActivity
import kotlinx.coroutines.launch

class CommunityFragment : ViewBindingFragment<FragmentCommunityBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCommunityBinding
        get() = FragmentCommunityBinding::inflate

    private val viewModel: CommunityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEventListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it)
                }
            }
        }

    }

    private fun updateUi(uiState: CommunityUiState) {


    }

    private fun initEventListeners() {
        binding.fab.setOnClickListener {
            navigateToCreate()
        }
    }

    private fun navigateToCreate() {
        val intent = CommunityCreateActivity.getIntent(requireContext())
        startActivity(intent)
    }

    private fun navigateToDetail() {
        val intent = CommunityDetailActivity.getIntent(requireContext())
        startActivity(intent)
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