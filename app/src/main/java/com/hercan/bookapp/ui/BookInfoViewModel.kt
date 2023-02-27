package com.hercan.bookapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hercan.bookapp.di.NetworkModule
import com.hercan.bookapp.models.BookList
import com.hercan.bookapp.retrofit.BookApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel@Inject constructor() : ViewModel() {

    var service: BookApi = NetworkModule.getRetrofit().create(BookApi::class.java)
    private val _booksData = MutableLiveData<BookList>()
    var booksData: LiveData<BookList> = _booksData
    var job: Job? = null

    fun getBooksWithId(id: Int) {
        /*job= CoroutineScope(Dispatchers.IO).launch {
            val response=bookApi.getBooksWithId(id)
            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    booksData.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }*/

        viewModelScope.launch(Dispatchers.IO) {
            service.getBooksWithId(id).enqueue(object : Callback<BookList> {
                override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
                    _booksData.postValue(response.body())
                }

                override fun onFailure(call: Call<BookList>, t: Throwable) {
                    Log.d("error", t.message.toString())
                }

            })

        }

    }
}