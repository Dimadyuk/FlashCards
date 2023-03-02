package com.example.flashcards.domain.use_cases

import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class GetCardUseCase(
    private val cardListRepository: CardListRepository
) {
    suspend fun getCardItem(id: Int): CardItem {
        return cardListRepository.getCardItem(id)
    }
}