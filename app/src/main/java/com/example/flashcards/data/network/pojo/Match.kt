package com.example.flashcards.data.network.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Match (
    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("segment")
    @Expose
    val segment: String? = null,

    @SerializedName("translation")
    @Expose
    val translation: String? = null,

    @SerializedName("source")
    @Expose
    val source: String? = null,

    @SerializedName("target")
    @Expose
    val target: String? = null,

    @SerializedName("quality")
    @Expose
    val quality: String? = null,

    @SerializedName("reference")
    @Expose
    val reference: Any? = null,

    @SerializedName("usage-count")
    @Expose
    val usageCount: Int? = null,

    @SerializedName("subject")
    @Expose
    val subject: String? = null,

    @SerializedName("created-by")
    @Expose
    val createdBy: String? = null,

    @SerializedName("last-updated-by")
    @Expose
    val lastUpdatedBy: String? = null,

    @SerializedName("create-date")
    @Expose
    val createDate: String? = null,

    @SerializedName("last-update-date")
    @Expose
    val lastUpdateDate: String? = null,

    @SerializedName("match")
    @Expose
    val match: Int? = null,
)