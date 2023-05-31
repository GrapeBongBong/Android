package com.example.android_bong.view.main.check.chatroom

import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.databinding.ItemChatRoomBinding

class CheckChatRoomViewHolder(
    private val binding: ItemChatRoomBinding,
    private val onClickItem: (ChatRoomItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: ChatRoomItemUiState) = with(binding) {

        title.text = uiState.roomName

        root.setOnClickListener { onClickItem(uiState) }

    }
}