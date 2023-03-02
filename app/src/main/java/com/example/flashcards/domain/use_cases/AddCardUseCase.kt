package com.example.flashcards.domain.use_cases

import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class AddCardUseCase(
    private val cardListRepository: CardListRepository
) {
    suspend fun addCard(cardItem: CardItem){
        cardListRepository.addCardItem(cardItem)
    }
}