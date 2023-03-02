package com.example.flashcards.data.network.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Datum(
    @SerializedName("responseData")
    @Expose
    val responseData: ResponseData? = null,

    @SerializedName("quotaFinished")
    @Expose
    val quotaFinished: Boolean? = null,

    @SerializedName("mtLangSupported")
    @Expose
    val mtLangSupported: Any? = null,

    @SerializedName("responseDetails")
    @Expose
    val responseDetails: String? = null,

    @SerializedName("responseStatus")
    @Expose
    val responseStatus: Int? = null,

    @SerializedName("responderId")
    @Expose
    val responderId: String? = null,

    @SerializedName("exception_code")
    @Expose
    val exceptionCode: Any? = null,

    @SerializedName("matches")
    @Expose
    val matches: List<Match>? = null,
    )