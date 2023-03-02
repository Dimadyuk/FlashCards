package com.example.flashcards.domain

import java.io.Serializable


data class CardItem(
    val word: String,
    val translation: String,
    var id: Int = UNDEFINED_ID
) : Serializable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}