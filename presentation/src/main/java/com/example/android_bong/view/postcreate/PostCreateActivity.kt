package com.example.android_bong.view.postcreate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.WindowCompat
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityPostCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostCreateActivity : ViewBindingActivity<ActivityPostCreateBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityPostCreateBinding
        get() = ActivityPostCreateBinding::inflate

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, PostCreateActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
    }
}