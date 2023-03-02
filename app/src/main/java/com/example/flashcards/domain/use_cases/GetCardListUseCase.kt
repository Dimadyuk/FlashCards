package com.example.flashcards.domain.use_cases

import androidx.lifecycle.LiveData
import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class GetCardListUseCase(
    private val cardListRepository: CardListRepository
) {
    suspend fun getCardList(): LiveData<List<CardItem>> {
        return cardListRepository.getCardList()
    }
}