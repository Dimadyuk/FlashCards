package com.example.flashcards.presentation.card_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.CardListRepositoryImpl
import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.use_cases.AddCardUseCase
import com.example.flashcards.domain.use_cases.EditCardUseCase
import com.example.flashcards.domain.use_cases.GetCardByWordUseCase
import kotlinx.coroutines.launch

class CardItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CardListRepositoryImpl(application)

    private val addCardUseCase = AddCardUseCase(repository)

    private val editCardUseCase = EditCardUseCase(repository)

    private val getCardItemByWordUseCase = GetCardByWordUseCase(repository)


    fun editCardItem(cardItem: CardItem) {
        viewModelScope.launch {
            editCardUseCase.editCard(cardItem)
        }
    }

    suspend fun getCardItemByWord(word: String): CardItem? {
        return getCardItemByWordUseCase.getCardItemByWord(word)
    }

    fun addCardItem(cardItem: CardItem) {
        viewModelScope.launch {
            addCardUseCase.addCard(cardItem)
        }
    }
}