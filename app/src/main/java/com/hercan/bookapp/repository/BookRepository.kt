package com.hercan.bookapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.hercan.bookapp.paging.BookPagingSource
import com.hercan.bookapp.retrofit.BookApi
import javax.inject.Inject

class BookRepository @Inject constructor(val bookApi: BookApi) {
    fun getBooks() = Pager(
        config = PagingConfig(pageSize = 2188),
        pagingSourceFactory = { BookPagingSource(bookApi) }
    ).liveData
}