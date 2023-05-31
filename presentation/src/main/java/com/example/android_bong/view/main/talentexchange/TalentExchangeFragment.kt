package com.example.android_bong.view.main.talentexchange

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentTalentExchangeBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.extension.addDividerDecoration
import com.example.android_bong.view.main.talentexchange.create.TalentExchangeCreateActivity
import com.example.android_bong.view.main.talentexchange.detail.TalentExchangeDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class TalentExchangeFragment : ViewBindingFragment<FragmentTalentExchangeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTalentExchangeBinding
        get() = FragmentTalentExchangeBinding::inflate

    private val viewModel: TalentExchangeViewModel by activityViewModels()

    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPosts()
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
                viewModel.fetchPosts()
                adapter.submitList(viewModel.uiState.value.posts)
                it.message?.let { message -> showSnackBar(message) }
            }
        }
    }

    private fun initRecyclerView(adapter: TalentExchangeAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addDividerDecoration()
        recyclerView.setOnScrollChangeListener(::onScrollChangeListener)

    }

    private fun updateUi(uiState: TalentExchangeUiState, adapter: TalentExchangeAdapter) =
        with(binding) {
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

    private fun initEventListeners() {
        binding.fab.setOnClickListener {
            navigateToCreate()
        }
        binding.loadState.retryButton.setOnClickListener {
            viewModel.fetchPosts()
        }

        binding.takeCateSpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.talent_with_all_array,
            android.R.layout.simple_spinner_item
        )

        binding.giveCateSpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.talent_with_all_array,
            android.R.layout.simple_spinner_item
        )

        binding.giveCateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateGiveCate(
                        binding.giveCateSpinner.getItemAtPosition(position).toString()
                    )
                    viewModel.fetchPosts()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.takeCateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateTakeCate(
                        binding.takeCateSpinner.getItemAtPosition(position).toString()
                    )
                    viewModel.fetchPosts()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }


    private fun navigateToCreate() {
        val intent = TalentExchangeCreateActivity.getIntent(requireContext())
        launcher?.launch(intent)
    }

    private fun onClickPost(talentExchangeItemUiState: TalentExchangeItemUiState) {
        val intent =
            TalentExchangeDetailActivity.getIntent(requireContext(), talentExchangeItemUiState.pid)
        launcher?.launch(intent)
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