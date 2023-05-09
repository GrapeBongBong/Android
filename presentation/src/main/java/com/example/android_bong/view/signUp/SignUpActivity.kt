package com.example.android_bong.view.signUp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivitySignUpBinding
import com.example.android_bong.extension.RefreshStateContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : ViewBindingActivity<ActivitySignUpBinding>() {

    private val viewModel: SignUpViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivitySignUpBinding
        get() = ActivitySignUpBinding::inflate

    private var launcher: ActivityResultLauncher<Intent>? = null

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initEventListeners() = with(binding) {
        signUpButton.setOnClickListener {
            viewModel.signUp()
        }

        dataPicker.setOnDateChangedListener { dataPicker, _, _, _ ->
            viewModel.updateBirth(
                "${dataPicker.year}-${dataPicker.month + 1}-${dataPicker.dayOfMonth}"
            )
        }



        name.addTextChangedListener {
            if (it != null) {
                viewModel.updateName(it.toString())
            }
        }
        phoneNumber.addTextChangedListener {
            if (it != null) {
                viewModel.updatePhoneNumber(it.toString())
            }
        }
        email.addTextChangedListener {
            if (it != null) {
                viewModel.updateEmail(it.toString())
            }
        }
        nickName.addTextChangedListener {
            if (it != null) {
                viewModel.updateNickName(it.toString())
            }
        }
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
        passwordCheck.addTextChangedListener {
            if (it != null) {
                viewModel.updatePasswordCheck(it.toString())
            }
        }
    }

    private fun updateUi(uiState: SignUpUiState) {
        binding.nameInputLayout.apply {
            isErrorEnabled = uiState.showNameError
            error = if (uiState.showNameError) {
                context.getString(R.string.name_is_not_valid)
            } else null
        }

        binding.phoneNumberInputLayout.apply {
            isErrorEnabled = uiState.showPhoneNumberError
            error = if (uiState.showPhoneNumberError) {
                context.getString(R.string.phone_is_not_valid)
            } else null
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

        binding.idInputLayout.apply {
            isErrorEnabled = uiState.showIdError
            error = if (uiState.showIdError) {
                context.getString(R.string.ID_is_not_valid)
            } else null
        }

        binding.passwordInputLayout.apply {
            isErrorEnabled = uiState.showPasswordError
            error = if (uiState.showPasswordError) {
                context.getString(R.string.password_is_not_valid)
            } else null
        }

        binding.passwordCheckInputLayout.apply {
            isErrorEnabled = uiState.showPasswordCheckError
            error = if (uiState.showPasswordCheckError) {
                context.getString(R.string.password_is_not_same)
            } else null
        }
        binding.signUpButton.apply {
            isEnabled = uiState.isInputValid
        }

    }

}