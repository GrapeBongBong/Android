package com.example.android_bong.view.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityPostDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : ViewBindingActivity<ActivityPostDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityPostDetailBinding
        get() = ActivityPostDetailBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}