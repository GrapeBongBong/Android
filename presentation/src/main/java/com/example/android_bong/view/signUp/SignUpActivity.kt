package com.example.android_bong.view.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivitySignUpBinding

class SignUpActivity : ViewBindingActivity<ActivitySignUpBinding>() {

    private val viewModel: SignUpViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivitySignUpBinding
        get() = ActivitySignUpBinding::inflate

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}