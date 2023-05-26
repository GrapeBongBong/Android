package com.example.android_bong.view.main.comment

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_bong.R
import com.example.android_bong.databinding.ItemCommentBinding

class CommentViewHolder(
    private val binding: ItemCommentBinding,
    private val onClickMenu: (CommentItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: CommentItemUiState) = with(binding) {
        val glide = Glide.with(root)

        if (uiState.isMine) {
            anonymous.text = root.context.getString(
                R.string.isMinePost
            )
            commentMenuButton.isVisible = true
        } else {
            commentMenuButton.isVisible = false
        }

        commentMenuButton.setOnClickListener {

        }

        date.text = uiState.date
        commentText.text = uiState.content

    }

}