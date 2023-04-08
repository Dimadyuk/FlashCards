package com.example.flashcards.domain.use_cases

import com.example.flashcards.domain.CardListRepository

class GetTranslationUseCase(private val cardListRepository: CardListRepository) {
    suspend operator fun invoke(langPair: String, word: String): Pair<String, String> {
        return cardListRepository.getTranslation(langPair, word)
    }
}