package com.hercan.bookapp.retrofit

import com.hercan.bookapp.models.BookList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET(".")
    suspend fun getBooks(@Query("page") page: Int): BookList

    @GET(".")
    fun getBooksWithId(@Query("ids") id: Int): Call<BookList>
}