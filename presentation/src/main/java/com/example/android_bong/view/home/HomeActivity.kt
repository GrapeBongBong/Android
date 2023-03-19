package com.example.android_bong.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityHomeBinding

class HomeActivity : ViewBindingActivity<ActivityHomeBinding>() {

    private val viewModel : HomeViewModel by viewModels()

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}