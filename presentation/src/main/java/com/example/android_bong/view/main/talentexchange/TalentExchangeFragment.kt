package com.example.android_bong.view.main.talentexchange

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentTalentExchangeBinding
import com.example.android_bong.extension.*
import com.example.android_bong.view.main.talentexchange.create.TalentExchangeCreateActivity
import com.example.android_bong.view.main.talentexchange.detail.TalentExchangeDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class TalentExchangeFragment : ViewBindingFragment<FragmentTalentExchangeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTalentExchangeBinding
        get() = FragmentTalentExchangeBinding::inflate

    private val viewModel: TalentExchangeViewModel by activityViewModels()

    private var launcher: ActivityResultLauncher<Intent>? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TalentExchangeAdapter(::onClickPost)

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
                adapter.refresh()
                it.message?.let { message -> showSnackBar(message) }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initRecyclerView(adapter: TalentExchangeAdapter) = with(binding) {
        recyclerView.adapter = adapter.withLoadStateFooter(
            PagingLoadStateAdapter { adapter.retry() }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addDividerDecoration()
        recyclerView.setOnScrollChangeListener(::onScrollChangeListener)

        loadState.setListeners(adapter, refresh)
        adapter.registerObserverForScrollToTop(recyclerView, whenItemRangeMoved = true)
    }

    private fun updateUi(uiState: TalentExchangeUiState, adapter: TalentExchangeAdapter) {
        adapter.submitData(viewLifecycleOwner.lifecycle, uiState.pagingData)
    }

    private fun initEventListeners() {
        binding.fab.setOnClickListener {
            navigateToCreate()
        }
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

    private fun navigateToCreate() {
        val intent = TalentExchangeCreateActivity.getIntent(requireContext())
        startActivity(intent)
    }

    private fun onClickPost(talentExchangeItemUiState: TalentExchangeItemUiState) {
        val intent = TalentExchangeDetailActivity.getIntent(requireContext())
        launcher?.launch(intent)
    }
}