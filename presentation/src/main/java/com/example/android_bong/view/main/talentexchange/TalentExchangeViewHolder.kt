package com.example.android_bong.view.main.talentexchange

import androidx.recyclerview.widget.RecyclerView
import com.example.android_bong.R
import com.example.android_bong.databinding.ItemExchangePostBinding

class TalentExchangeViewHolder(
    private val binding: ItemExchangePostBinding,
    private val onClickItem: (TalentExchangeItemUiState) -> Unit

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(uiState: TalentExchangeItemUiState) = with(binding) {

        userNickName.text = root.context.getString(
            R.string.post_id_nickName,
            uiState.writerNick,
            uiState.writerNick
        )

        title.text = uiState.title
        content.text = uiState.content

        takeTalent.text = root.context.getString(
            R.string.take_text,
            uiState.takeTalent
        )
        giveTalent.text = root.context.getString(
            R.string.give_text,
            uiState.giveTalent
        )

        date.text = uiState.date

        root.setOnClickListener { onClickItem(uiState) }

    }

}
