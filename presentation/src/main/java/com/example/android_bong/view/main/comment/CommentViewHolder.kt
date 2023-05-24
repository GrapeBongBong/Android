package com.example.android_bong.view.main.comment

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bong.R
import com.example.android_bong.databinding.ItemCommentBinding
import com.example.android_bong.view.main.talentexchange.CommentItemUiState

class CommentViewHolder(
    private val binding: ItemCommentBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: CommentItemUiState) = with(binding) {
        val glide = Glide.with(root)

        if (uiState.isMine) {
            anonymous.text = root.context.getString(
                R.string.isMinePost
            )
        }
        date.text = uiState.date
        commentText.text = uiState.content

    }

}