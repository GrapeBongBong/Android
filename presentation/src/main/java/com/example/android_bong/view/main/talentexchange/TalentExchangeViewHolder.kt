package com.example.android_bong.view.main.talentexchange

import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.databinding.ItemExchangePostBinding

class TalentExchangeViewHolder(
    private val binding: ItemExchangePostBinding,
    private val onClickItem: (TalentExchangeItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: TalentExchangeItemUiState) = with(binding) {


    }

}
