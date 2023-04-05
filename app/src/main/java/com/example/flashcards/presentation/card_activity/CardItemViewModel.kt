package com.example.flashcards.presentation.card_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _errorInputWord = MutableLiveData<Boolean>()
    val errorInputWord: LiveData<Boolean>
        get() = _errorInputWord

    private val _errorInputTranslation = MutableLiveData<Boolean>()
    val errorInputTranslation: LiveData<Boolean>
        get() = _errorInputTranslation


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

    fun resetErrorInputWord() {
        _errorInputWord.value = false
    }

    fun resetErrorInputTranslation() {
        _errorInputTranslation.value = false
    }
}