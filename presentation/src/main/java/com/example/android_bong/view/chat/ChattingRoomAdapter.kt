package com.example.android_bong.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android_bong.databinding.ItemChatRoomBinding

class ChattingRoomAdapter(
    private val onClickItem: (PostChatRoomItemUiState) -> Unit
) : ListAdapter<PostChatRoomItemUiState, ChattingRoomViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingRoomViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChatRoomBinding.inflate(layoutInflater, parent, false)
        return ChattingRoomViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: ChattingRoomViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun submitList(list: List<PostChatRoomItemUiState>?) {
        super.submitList(list)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PostChatRoomItemUiState>() {
            override fun areItemsTheSame(
                oldItem: PostChatRoomItemUiState,
                newItem: PostChatRoomItemUiState
            ): Boolean {
                return oldItem.roomId == newItem.roomId
            }

            override fun areContentsTheSame(
                oldItem: PostChatRoomItemUiState,
                newItem: PostChatRoomItemUiState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}