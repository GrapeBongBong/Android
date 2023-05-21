package com.example.android_bong.view.main.community.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityCreateBinding
import com.example.android_bong.view.main.MainActivity

class CommunityCreateActivity : ViewBindingActivity<ActivityCommunityCreateBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommunityCreateBinding
        get() = ActivityCommunityCreateBinding::inflate


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}