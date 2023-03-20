package com.example.android_bong.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityLoginBinding
import com.example.android_bong.view.main.MainActivity

class LoginActivity : ViewBindingActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        binding.signInButton.setOnClickListener {
            navigateToMainView()
        }

    }

    private fun navigateToMainView() {
        val intent = MainActivity.getIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        }
        startActivity(intent)
        finish()
    }
}