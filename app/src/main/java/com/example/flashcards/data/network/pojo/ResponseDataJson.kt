package com.example.flashcards.data.network.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseDataJson(
    @SerializedName("responseData")
    @Expose
    val cardInfoJsonObject: JsonObject? = null,
)