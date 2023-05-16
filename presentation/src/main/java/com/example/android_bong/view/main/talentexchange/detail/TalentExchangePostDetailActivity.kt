package com.example.android_bong.view.main.talentexchange.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityTalentExchangePostDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalentExchangePostDetailActivity :
    ViewBindingActivity<ActivityTalentExchangePostDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTalentExchangePostDetailBinding
        get() = ActivityTalentExchangePostDetailBinding::inflate

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TalentExchangePostDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}