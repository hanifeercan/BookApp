package com.hercan.bookapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hercan.bookapp.retrofit.BookApi

class BookPagingSource(val bookApi: BookApi): PagingSource<Int,com.hercan.bookapp.models.Result>() {
    override fun getRefreshKey(state: PagingState<Int, com.hercan.bookapp.models.Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.hercan.bookapp.models.Result> {
        return try {
            val position = params.key ?: 1
            val response = bookApi.getBooks(position)
            LoadResult.Page(
                data = response.result,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.count) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}