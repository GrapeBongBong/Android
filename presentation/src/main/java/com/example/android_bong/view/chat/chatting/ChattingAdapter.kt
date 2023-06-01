package com.example.android_bong.view.chat.chatting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.databinding.ItemMyChatMessageBinding
import com.example.android_bong.databinding.ItemOtherChatMessageBinding

class ChattingAdapter(private val myUsrId: String) :
    ListAdapter<ChattingItemUiState, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == MY_CHAT) {
            val binding = ItemMyChatMessageBinding.inflate(layoutInflater, parent, false)
            MyChatItemViewHolder(binding)
        } else {
            val binding = ItemOtherChatMessageBinding.inflate(layoutInflater, parent, false)
            OtherChatItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MY_CHAT) {
            (holder as MyChatItemViewHolder).bind(currentList[position])
        } else {
            (holder as OtherChatItemViewHolder).bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (myUsrId == currentList[position].senderId) MY_CHAT
        else OTHER_CHAT
    }

    override fun submitList(list: List<ChattingItemUiState>?) {
        super.submitList(list)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ChattingItemUiState>() {
            override fun areItemsTheSame(
                oldItem: ChattingItemUiState,
                newItem: ChattingItemUiState
            ): Boolean {
                return oldItem.messageId == newItem.messageId
            }

            override fun areContentsTheSame(
                oldItem: ChattingItemUiState,
                newItem: ChattingItemUiState
            ): Boolean {
                return oldItem == newItem
            }
        }
        private const val MY_CHAT = 1
        private const val OTHER_CHAT = 2
    }

    inner class MyChatItemViewHolder(private val binding: ItemMyChatMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uiState: ChattingItemUiState) = with(binding) {
            textChatMessageMe.text = uiState.message
        }
    }

    inner class OtherChatItemViewHolder(private val binding: ItemOtherChatMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uiState: ChattingItemUiState) = with(binding) {
            textChatUserOther.text = uiState.senderId
            textChatMessageOther.text = uiState.message
        }
    }
}