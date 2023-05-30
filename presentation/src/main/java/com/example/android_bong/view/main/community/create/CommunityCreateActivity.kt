package com.example.android_bong.view.main.community.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.GlideApp
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityCreateBinding
import com.example.android_bong.extension.setResultRefresh
import com.example.android_bong.extension.toBitmap
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityCreateActivity : ViewBindingActivity<ActivityCommunityCreateBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommunityCreateBinding
        get() = ActivityCommunityCreateBinding::inflate


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CommunityCreateActivity::class.java)
        }
    }

    private val viewModel: CommunityCreateViewModel by viewModels()

    private val pickMedia1 =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                viewModel.updateImages1(imageUri.toBitmap(this))
            }
            val glide = GlideApp.with(this)
            glide.load(imageUri)
                .fallback(R.drawable.ic_baseline_add_24)
                .into(binding.imageView1)
        }

    private val pickMedia2 =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                viewModel.updateImages2(imageUri.toBitmap(this))
            }
            Log.d("imageUri", imageUri.toString())
            val glide = GlideApp.with(this)
            glide.load(imageUri)
                .fallback(R.drawable.ic_baseline_add_24)
                .into(binding.imageView2)
        }

    private val pickMedia3 =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                viewModel.updateImages3(imageUri.toBitmap(this))
            }
            Log.d("imageUri", imageUri.toString())
            val glide = GlideApp.with(this)
            glide.load(imageUri)
                .fallback(R.drawable.ic_baseline_add_24)
                .into(binding.imageView3)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.community_posting)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        initEvent()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it)
                }
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

    private fun updateUi(uiState: CommunityCreateUiState) = with(binding) {

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            viewModel.userMessageShown()
        }

        if (uiState.isSuccessPosting) {
            setResultRefresh()
            finish()
        }

        createButton.apply {
            isEnabled = uiState.isInputValid && !uiState.isLoading
            setText(if (uiState.isLoading) R.string.loading else R.string.posting)
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

        createButton.setOnClickListener {
            viewModel.createPost()
        }

        imageView1.setOnClickListener {
            showImagePicker1()
        }

        imageView2.setOnClickListener {
            showImagePicker2()
        }

        imageView3.setOnClickListener {
            showImagePicker3()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showImagePicker1() {
        pickMedia1.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImagePicker2() {
        pickMedia2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImagePicker3() {
        pickMedia3.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

}