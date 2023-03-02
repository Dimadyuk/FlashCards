package com.example.flashcards.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_items")
data class CardItemDbModel(
    val word: String,
    val translation: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int
) {
}