package com.hercan.bookapp.models

import com.google.gson.annotations.SerializedName

data class BookList (
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val result: List<Result>
)