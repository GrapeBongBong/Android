package com.example.android_bong.view.chat

import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.databinding.ItemChatRoomBinding

class ChattingRoomViewHolder(
    private val binding: ItemChatRoomBinding,
    private val onClickItem: (PostChatRoomItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: PostChatRoomItemUiState) = with(binding) {

        title.text = uiState.roomName

        root.setOnClickListener { onClickItem(uiState) }

    }
}