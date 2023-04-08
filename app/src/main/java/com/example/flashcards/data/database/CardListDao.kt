package com.example.flashcards.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flashcards.domain.CardItem


@Dao
interface CardListDao {

    @Query("SELECT * FROM card_items")
    fun getCardList(): LiveData<List<CardItemDbModel>>

    @Query("SELECT * FROM card_items")
    fun getCardListDb(): List<CardItemDbModel>

    @Query("SELECT * FROM card_items WHERE id = :cardId LIMIT 1")
    suspend fun getCardItem(cardId: Int): CardItemDbModel

    @Query("SELECT * FROM card_items WHERE word = :word LIMIT 1")
    suspend fun getCardItemByWord(word: String): CardItemDbModel?

    @Query("DELETE FROM card_items WHERE id = :cardId")
    suspend fun deleteCardItem(cardId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCardItem(cardItem: CardItemDbModel)

    @Query("SELECT * FROM card_items WHERE translation = :translation LIMIT 1")
    suspend fun getCardItemByTranslation(translation: String): CardItemDbModel?


}