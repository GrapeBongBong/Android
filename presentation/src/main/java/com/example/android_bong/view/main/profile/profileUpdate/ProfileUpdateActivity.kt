package com.example.android_bong.view.main.profile.profileUpdate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityProfileUpdateBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileUpdateActivity : ViewBindingActivity<ActivityProfileUpdateBinding>() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ProfileUpdateActivity::class.java)
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityProfileUpdateBinding
        get() = ActivityProfileUpdateBinding::inflate

    private val viewModel: ProfileUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.update_profile)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)

        initEventListeners()
        initSetting(viewModel.uiState.value)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::updateUi)
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

    private fun initSetting(uiState: ProfileUpdateUiState) = with(binding) {
        if (uiState.currentUser != null) {
            val user = uiState.currentUser
            nickName.setText(user.nickName)
            address.setText(user.address)
            nameText.text = getString(R.string.see_name, user.name)
            birthText.text = getString(R.string.see_birth, user.birth)
            sexText.text = getString(R.string.see_gender, user.gender)
            phoneNumberText.text = getString(R.string.see_phoneNumber, user.phone_num)
            emailText.text = getString(R.string.see_email, user.email)
            IdText.text = getString(R.string.see_id, user.id)
        }
    }

    private fun updateUi(uiState: ProfileUpdateUiState) = with(binding) {

        if (uiState.userMessage != null) {
            Log.d("error", uiState.userMessage)
            showSnackBar(uiState.userMessage)
            viewModel.userMessageShown()
        }

        if (uiState.updatingIsSuccess) {
            finish()
        }

        updatingButton.apply {
            isEnabled = !uiState.isLoading
        }
    }

    private fun initEventListeners() = with(binding) {
        updatingButton.setOnClickListener {
            viewModel.updateProfile()
        }

        nickName.addTextChangedListener {
            if (it != null) {
                viewModel.updateNickName(it.toString())
            }
        }

        address.addTextChangedListener {
            if (it != null) {
                viewModel.updateAddress(it.toString())
            }
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}

