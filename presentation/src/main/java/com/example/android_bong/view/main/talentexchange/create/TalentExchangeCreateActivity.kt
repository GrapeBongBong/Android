package com.example.android_bong.view.main.talentexchange.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityTalentExchangeCreateBinding
import com.example.android_bong.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalentExchangeCreateActivity : ViewBindingActivity<ActivityTalentExchangeCreateBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTalentExchangeCreateBinding
        get() = ActivityTalentExchangeCreateBinding::inflate

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}