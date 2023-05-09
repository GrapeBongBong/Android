package com.example.android_bong.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityLoginBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.view.main.MainActivity
import com.example.android_bong.view.signUp.SignUpActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ViewBindingActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    private var launcher: ActivityResultLauncher<Intent>? = null

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        initEventListeners()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::updateUi)
            }
        }

        launcher = registerForActivityResult(RefreshStateContract()) {
            if (it != null) {
                it.message?.let { message ->
                    viewModel.showUserMessage(message)
                }
            }
        }
    }

    private fun updateUi(uiState: LoginUiState) {
        binding.idInputLayout.apply {
            isErrorEnabled = uiState.showEmailError
            error = if (uiState.showEmailError) {
                context.getString(R.string.ID_is_not_valid)
            } else null
        }
        binding.passwordInputLayout.apply {
            isErrorEnabled = uiState.showPasswordError
            error = if (uiState.showPasswordError) {
                context.getString(R.string.password_is_not_valid)
            } else null
        }
        if (uiState.successToSignIn) {
            navigateMainActivity()
        }
        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            viewModel.userMessageShown()
        }
        binding.signInButton.apply {
            isEnabled = uiState.isInputValid && !uiState.isLoading
            setText(if (uiState.isLoading) R.string.loading else R.string.login)
        }
    }

    private fun initEventListeners() = with(binding) {
        id.addTextChangedListener {
            if (it != null) {
                viewModel.updateID(it.toString())
            }
        }
        password.addTextChangedListener {
            if (it != null) {
                viewModel.updatePassword(it.toString())
            }
        }
        signInButton.setOnClickListener {
            viewModel.login()
        }
        signUpText.setOnClickListener {
            navigateSignUpActivity()
        }
    }

    private fun navigateMainActivity() {
        val intent = MainActivity.getIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        }
        startActivity(intent)
        finish()
    }

    private fun navigateSignUpActivity() {
        val intent = SignUpActivity.getIntent(this)
        startActivity(intent)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}