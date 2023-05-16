package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.ExchangePostApi
import com.example.data.extension.errorMessage
import com.example.data.mapper.toEntity
import com.example.domain.model.exchange.ExchangePost
import javax.inject.Inject

class ExchangePagingSource @Inject constructor(
    private val api: ExchangePostApi
) : PagingSource<Int, ExchangePost>() {

    companion object {
        private const val START_PAGE = 1
        const val PAGE_SIZE = 3
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExchangePost> {
        val page = params.key ?: START_PAGE
        return try {
            val response = api.getAll(size = PAGE_SIZE, page = page)
            if (response.isSuccessful) {
                val posts = response.body()!!.data.posts.map { it.toEntity() }
                val isEnd = posts.isEmpty()

                LoadResult.Page(
                    data = posts,
                    prevKey = if (page == START_PAGE) null else page - 1,
                    nextKey = if (isEnd) null else page + 1
                )
            } else {
                throw Exception(response.errorMessage)
            }
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ExchangePost>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}