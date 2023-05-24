package com.example.android_bong.view.main.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android_bong.databinding.ItemCommentBinding
import com.example.android_bong.view.main.talentexchange.detail.CommentItemUiState

class CommentAdapter :
    ListAdapter<CommentItemUiState, CommentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun submitList(list: List<CommentItemUiState>?) {
        super.submitList(list)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CommentItemUiState>() {
            override fun areItemsTheSame(
                oldItem: CommentItemUiState,
                newItem: CommentItemUiState
            ): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(
                oldItem: CommentItemUiState,
                newItem: CommentItemUiState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}