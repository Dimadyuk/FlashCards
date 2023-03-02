package com.example.flashcards.domain.use_cases

import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class DeleteCardUseCase(
    private val cardListRepository: CardListRepository
) {
    suspend fun deleteCard(cardItem: CardItem) {
        cardListRepository.deleteCardItem(cardItem)
    }
}