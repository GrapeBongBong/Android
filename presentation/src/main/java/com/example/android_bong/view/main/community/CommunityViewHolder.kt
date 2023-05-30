package com.example.android_bong.view.main.community

import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.R
import com.example.android_bong.common.GlideApp
import com.example.android_bong.databinding.ItemCommunityPostBinding
import com.example.android_bong.extension.convertDateTimeFormat

class CommunityViewHolder(
    private val binding: ItemCommunityPostBinding,
    private val onClickItem: (CommunityItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: CommunityItemUiState) = with(binding) {

        val glide = GlideApp.with(root)

        title.text = uiState.title
        content.text = uiState.content

        date.text = convertDateTimeFormat(uiState.date)

        if (uiState.liked) {
            glide.load(R.drawable.leaf_fill)
                .into(likeImage)
        } else {
            glide.load(R.drawable.leaf_border)
                .into(likeImage)
        }

        likedCount.text = uiState.likeCount.toString()

        root.setOnClickListener { onClickItem(uiState) }

    }
}