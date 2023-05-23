package com.example.android_bong.view.main.talentexchange

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android_bong.databinding.ItemExchangePostBinding

class TalentExchangeAdapter(
    private val onClickItem: (TalentExchangeItemUiState) -> Unit
) : ListAdapter<TalentExchangeItemUiState, TalentExchangeViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalentExchangeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExchangePostBinding.inflate(layoutInflater, parent, false)
        return TalentExchangeViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: TalentExchangeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun submitList(list: List<TalentExchangeItemUiState>?) {
        super.submitList(list)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TalentExchangeItemUiState>() {
            override fun areItemsTheSame(
                oldItem: TalentExchangeItemUiState,
                newItem: TalentExchangeItemUiState
            ): Boolean {
                return oldItem.pid == newItem.pid
            }

            override fun areContentsTheSame(
                oldItem: TalentExchangeItemUiState,
                newItem: TalentExchangeItemUiState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}