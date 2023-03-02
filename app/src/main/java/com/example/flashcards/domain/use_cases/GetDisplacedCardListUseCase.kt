package com.example.flashcards.domain.use_cases

import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class GetDisplacedCardListUseCase(
    private val cardListRepository: CardListRepository
) {
    suspend fun getDisplacedCardList(): List<CardItem>{
        return cardListRepository.getDisplacedCardList()
    }
}