package com.example.android_bong.view.main.community

import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.R
import com.example.android_bong.databinding.ItemCommunityPostBinding

class CommunityViewHolder(
    private val binding: ItemCommunityPostBinding,
    private val onClickItem: (CommunityItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: CommunityItemUiState) = with(binding) {

        title.text = uiState.title
        content.text = uiState.content
        date.text = uiState.date

        root.setOnClickListener { onClickItem(uiState) }

    }
}