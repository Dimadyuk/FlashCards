package com.example.flashcards.data.network.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("translatedText")
    @Expose
    val translatedText: String? = null,

    @SerializedName("match")
    @Expose
    val match: Int? = null,
)

