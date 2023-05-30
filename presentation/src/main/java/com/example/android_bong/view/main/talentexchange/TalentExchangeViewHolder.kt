package com.example.android_bong.view.main.talentexchange

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.R
import com.example.android_bong.common.GlideApp
import com.example.android_bong.databinding.ItemExchangePostBinding
import com.example.android_bong.extension.convertDateTimeFormat

class TalentExchangeViewHolder(
    private val binding: ItemExchangePostBinding,
    private val onClickItem: (TalentExchangeItemUiState) -> Unit

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: TalentExchangeItemUiState) = with(binding) {

        if (uiState.isMine) {
            userNickName.text = root.context.getString(
                R.string.isMinePost
            )
        } else {
            userNickName.text = root.context.getString(
                R.string.post_id_nickName,
                uiState.writerNick,
                uiState.writerNick
            )
        }

        val glide = GlideApp.with(root)

        if (uiState.writerImageURL != null) {
            glide.load(uiState.writerImageURL.toUri())
                .circleCrop()
                .fallback(R.drawable.ic_baseline_person_24)
                .into(userImage)
        }



        title.text = uiState.title
        content.text = uiState.content

        takeTalent.text = root.context.getString(
            R.string.take_text,
            uiState.takeCate,
            uiState.takeTalent
        )
        giveTalent.text = root.context.getString(
            R.string.give_text,
            uiState.giveCate,
            uiState.giveTalent
        )

        date.text = convertDateTimeFormat(uiState.date)

        if (uiState.liked) {
            glide.load(R.drawable.leaf_fill)
                .into(likeImage)
        } else {
            glide.load(R.drawable.leaf_border)
                .into(likeImage)
        }

        if (uiState.status) {
            status.text = root.context.getString(R.string.trading)
            status.setBackgroundColor(root.context.getColor(R.color.doing_trading_color))
        } else {
            status.text = root.context.getString(R.string.trading_completed)
            status.setBackgroundColor(root.context.getColor(R.color.seed))
        }

        root.setOnClickListener { onClickItem(uiState) }

        likedCount.text = uiState.likeCount.toString()

    }

}
