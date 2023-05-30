package com.example.android_bong.view.main.profile.profileUpdate

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
import com.example.android_bong.databinding.ActivityProfileUpdateBinding
import com.example.android_bong.extension.setResultRefresh
import com.example.android_bong.extension.toBitmap
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

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                viewModel.selectedImage = imageUri.toBitmap(this)

            } else {
                viewModel.selectedImage = null
            }

            Log.d("imageUri", imageUri.toString())
            val glide = GlideApp.with(this)
            glide.load(imageUri)
                .circleCrop()
                .fallback(R.drawable.ic_baseline_person_24)
                .into(binding.imageView)
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

    private fun initSetting(uiState: ProfileUpdateUiState) = with(binding) {
        if (uiState.currentUser != null) {
            val user = uiState.currentUser
            nickName.setText(user.nickName)
            phoneNumber.setText(user.phone_num)
            email.setText(user.email)

            val glide = GlideApp.with(this@ProfileUpdateActivity)
            glide.load(uiState.currentUser.profile_img)
                .circleCrop()
                .fallback(R.drawable.ic_baseline_person_24)
                .into(binding.imageView)

            nameText.text = getString(R.string.see_name, user.name)
            birthText.text = getString(R.string.see_birth, user.birth)
            sexText.text = getString(R.string.see_gender, user.gender)
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
            setResultRefresh()
            finish()
        }

        binding.emailInputLayout.apply {
            isErrorEnabled = uiState.showEmailError
            error = if (uiState.showEmailError) {
                context.getString(R.string.email_is_not_valid)
            } else null
        }

        binding.nickNameInputLayout.apply {
            isErrorEnabled = uiState.showNickNameError
            error = if (uiState.showNickNameError) {
                context.getString(R.string.nickName_is_not_valid)
            } else null
        }

        binding.passwordInputLayout.apply {
            isErrorEnabled = uiState.showPasswordError
            error = if (uiState.showPasswordError) {
                context.getString(R.string.password_is_not_valid)
            } else null
        }

        binding.phoneNumberInputLayout.apply {
            isErrorEnabled = uiState.showPhoneNumberError
            error = if (uiState.showPhoneNumberError) {
                context.getString(R.string.phone_is_not_valid)
            } else null
        }

        binding.updatingButton.apply {
            isEnabled = uiState.isInputValid && !uiState.isLoading
            setText(if (uiState.isLoading) R.string.loading else R.string.updating)
        }
    }

    private fun initEventListeners() = with(binding) {
        updatingButton.setOnClickListener {
            viewModel.updateProfile()
        }

        addPhotoImage.setOnClickListener {
            showImagePicker()
        }

        nickName.addTextChangedListener {
            if (it != null) {
                viewModel.updateNickName(it.toString())
            }
        }

        password.addTextChangedListener {
            if (it != null) {
                viewModel.updatePassword(it.toString())
            }
        }

        email.addTextChangedListener {
            if (it != null) {
                viewModel.updateEmail(it.toString())
            }
        }

        phoneNumber.addTextChangedListener {
            if (it != null) {
                viewModel.updatePhoneNumber(it.toString())
            }
        }
    }

    private fun showImagePicker() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}

