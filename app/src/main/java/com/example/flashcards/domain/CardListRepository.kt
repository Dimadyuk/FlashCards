package com.example.flashcards.domain

import androidx.lifecycle.LiveData

interface CardListRepository {

    suspend fun addCardItem(cardItem: CardItem)

    suspend fun deleteCardItem(cardItem: CardItem)

    suspend fun editCardItem(cardItem: CardItem)

    suspend fun getCardItem(id: Int): CardItem

    fun getCardList(): LiveData<List<CardItem>>

    suspend fun getDisplacedCardList(): List<CardItem>

    suspend fun getCardItemByWord(word: String): CardItem?

    suspend fun getTranslation(langPair: String, word: String): Pair<String, String>
}