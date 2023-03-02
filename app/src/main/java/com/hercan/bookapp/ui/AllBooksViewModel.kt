package com.hercan.bookapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hercan.bookapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AllBooksViewModel @Inject constructor(val bookRepository: BookRepository): ViewModel() {
    val list = bookRepository.getBooks().cachedIn(viewModelScope)
}