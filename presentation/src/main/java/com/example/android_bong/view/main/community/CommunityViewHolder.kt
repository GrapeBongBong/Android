package com.example.android_bong.view.main.community

import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.R
import com.example.android_bong.common.GlideApp
import com.example.android_bong.databinding.ItemCommunityPostBinding

class CommunityViewHolder(
    private val binding: ItemCommunityPostBinding,
    private val onClickItem: (CommunityItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: CommunityItemUiState) = with(binding) {

        val glide = GlideApp.with(root)

        title.text = uiState.title
        content.text = uiState.content
        date.text = uiState.date

        if (uiState.liked) {
            glide.load(R.drawable.ic_baseline_like_filled_24)
                .into(likeImage)
        } else {
            glide.load(R.drawable.ic_baseline_like_border_24)
                .into(likeImage)
        }

        likedCount.text = uiState.likeCount.toString()

        root.setOnClickListener { onClickItem(uiState) }

    }
}