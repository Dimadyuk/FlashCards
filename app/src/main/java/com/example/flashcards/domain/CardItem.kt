package com.example.flashcards.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardItem(
    val word: String,
    val translation: String,
    var id: Int = UNDEFINED_ID
) : Parcelable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}