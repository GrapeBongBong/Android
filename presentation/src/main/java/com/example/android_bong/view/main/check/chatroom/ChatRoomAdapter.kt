package com.example.android_bong.view.main.check.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android_bong.databinding.ItemChatRoomBinding

class ChatRoomAdapter(
    private val onClickItem: (ChatRoomItemUiState) -> Unit
) : ListAdapter<ChatRoomItemUiState, ChatRoomViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChatRoomBinding.inflate(layoutInflater, parent, false)
        return ChatRoomViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun submitList(list: List<ChatRoomItemUiState>?) {
        super.submitList(list)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ChatRoomItemUiState>() {
            override fun areItemsTheSame(
                oldItem: ChatRoomItemUiState,
                newItem: ChatRoomItemUiState
            ): Boolean {
                return oldItem.roomId == newItem.roomId
            }

            override fun areContentsTheSame(
                oldItem: ChatRoomItemUiState,
                newItem: ChatRoomItemUiState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}