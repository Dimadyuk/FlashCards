package com.example.flashcards.domain.use_cases

import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class EditCardUseCase(
    private val cardListRepository: CardListRepository
) {
    suspend fun editCard(cardItem: CardItem) {
        cardListRepository.editCardItem(cardItem)
    }
}