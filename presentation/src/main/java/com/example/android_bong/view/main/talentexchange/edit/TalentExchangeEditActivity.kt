package com.example.android_bong.view.main.talentexchange.edit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bong.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalentExchangeEditActivity : AppCompatActivity() {


    private val viewModel: TalentExchangeEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talent_exchange_edit)
    }
}