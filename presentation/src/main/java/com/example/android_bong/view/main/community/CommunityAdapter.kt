package com.example.android_bong.view.main.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android_bong.databinding.ItemCommunityPostBinding

class CommunityAdapter(
    private val onClickItem: (CommunityItemUiState) -> Unit
) : ListAdapter<CommunityItemUiState, CommunityViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCommunityPostBinding.inflate(layoutInflater, parent, false)
        return CommunityViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun submitList(list: List<CommunityItemUiState>?) {
        super.submitList(list)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CommunityItemUiState>() {
            override fun areItemsTheSame(
                oldItem: CommunityItemUiState,
                newItem: CommunityItemUiState
            ): Boolean {
                return oldItem.pid == newItem.pid
            }

            override fun areContentsTheSame(
                oldItem: CommunityItemUiState,
                newItem: CommunityItemUiState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}