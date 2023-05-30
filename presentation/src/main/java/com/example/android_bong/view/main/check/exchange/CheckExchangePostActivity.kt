package com.example.android_bong.view.main.check.exchange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCheckExchangePostBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.extension.addDividerDecoration
import com.example.android_bong.extension.setResultRefresh
import com.example.android_bong.view.main.talentexchange.TalentExchangeAdapter
import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState
import com.example.android_bong.view.main.talentexchange.detail.TalentExchangeDetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckExchangePostActivity : ViewBindingActivity<ActivityCheckExchangePostBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCheckExchangePostBinding
        get() = ActivityCheckExchangePostBinding::inflate

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CheckExchangePostActivity::class.java)
        }
    }

    private var launcher: ActivityResultLauncher<Intent>? = null

    private val viewModel: CheckExchangePostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchPosts()
        binding.toolbar.setTitle(R.string.checkExchangePost)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        val adapter = TalentExchangeAdapter(::onClickPost)
        initEventListeners()
        initRecyclerView(adapter = adapter)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        setResultRefresh()
        return super.onKeyDown(keyCode, event)
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

    private fun initRecyclerView(adapter: TalentExchangeAdapter) = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@CheckExchangePostActivity)
        recyclerView.addDividerDecoration()

    }

    private fun updateUi(uiState: CheckExchangePostUiState, adapter: TalentExchangeAdapter) =
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
        binding.loadState.retryButton.setOnClickListener {
            viewModel.fetchPosts()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun onClickPost(talentExchangeItemUiState: TalentExchangeItemUiState) {
        val intent =
            TalentExchangeDetailActivity.getIntent(this, talentExchangeItemUiState.pid)
        launcher?.launch(intent)
    }

}