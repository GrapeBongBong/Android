package com.example.android_bong.extension

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android_bong.R
import com.example.android_bong.databinding.ContentLoadStateBinding
import java.net.ConnectException

fun <PA : PagingDataAdapter<T, VH>, T, VH> ContentLoadStateBinding.setListeners(
    adapter: PA,
    swipeToRefresh: SwipeRefreshLayout,
) {
    swipeToRefresh.setOnRefreshListener { adapter.refresh() }

    this.retryButton.setOnClickListener {
        adapter.retry()
    }

    adapter.addLoadStateListener { loadStates ->
        val refreshLoadState = loadStates.refresh
        val isError = refreshLoadState is LoadState.Error
        val shouldShowEmptyText =
            refreshLoadState is LoadState.NotLoading && adapter.getItemCount() < 1

        emptyText.isVisible = shouldShowEmptyText
        swipeToRefresh.isRefreshing = refreshLoadState is LoadState.Loading
        retryButton.isVisible = isError
        errorMsg.isVisible = isError
        if (refreshLoadState is LoadState.Error) {
            errorMsg.text = when (val exception = refreshLoadState.error) {
                is ConnectException -> root.context.getString(R.string.fail_to_connect)
                else -> exception.message
            }
        }
    }
}

fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.registerObserverForScrollToTop(
    recyclerView: RecyclerView,
    whenItemInsertedFirst: Boolean = true,
    whenItemRangeMoved: Boolean = false
) {
    this.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (whenItemInsertedFirst && positionStart == 0) {
                recyclerView.scrollToPosition(0)
            }
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (whenItemRangeMoved) {
                recyclerView.scrollToPosition(0)
            }
        }
    })
}
