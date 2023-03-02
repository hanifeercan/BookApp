package com.hercan.bookapp.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("authors")
    val authors: List<Author?>?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("languages")
    val languages: List<String?>?,
    @SerializedName("subjects")
    val subjects: List<String?>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("formats")
    val formats: Formats?,
)
