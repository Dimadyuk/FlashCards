package com.example.flashcards.domain.use_cases

import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class GetCardByWordUseCase(
    private val cardListRepository: CardListRepository
) {
    suspend fun getCardItemByWord(word: String): CardItem? {
        return cardListRepository.getCardItemByWord(word)
    }
}