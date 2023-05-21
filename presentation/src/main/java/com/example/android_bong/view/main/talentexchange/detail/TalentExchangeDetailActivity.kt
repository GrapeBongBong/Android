package com.example.android_bong.view.main.talentexchange.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityTalentExchangeDetailBinding
import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Serializable

@AndroidEntryPoint
class TalentExchangeDetailActivity :
    ViewBindingActivity<ActivityTalentExchangeDetailBinding>() {


    private fun <T : Serializable?> Intent.getSerializable(key: String, m_class: Class<T>): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.getSerializableExtra(key, m_class)!!
        else
            this.getSerializableExtra(key) as T
    }

    override val bindingInflater: (LayoutInflater) -> ActivityTalentExchangeDetailBinding
        get() = ActivityTalentExchangeDetailBinding::inflate

    companion object {
        fun getIntent(context: Context, item: TalentExchangeItemUiState): Intent {
            return Intent(context, TalentExchangeDetailActivity::class.java).apply {
                putExtra("item", item)
            }
        }
    }

    private val viewModel: TalentExchangeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item = intent.getSerializable("item", TalentExchangeItemUiState::class.java)
        viewModel.bind(item)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(uiState: TalentExchangeDetailUiState) = with(binding) {
        if (uiState.item != null) {
            val item = uiState.item
            title.text = item.title
            content.text = item.content
            nickName.text = getString(R.string.post_id_nickName, item.writerNick, item.writerId)
            date.text = item.date
            takeTalent.text = getString(R.string.take_text, item.takeTalent)
            giveTalent.text = getString(R.string.give_text, item.giveTalent)

            //TODO: 이미지 처리 필요

        }

    }
}