package com.example.flashcards.presentation.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.CardListRepositoryImpl
import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.use_cases.DeleteCardUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CardListRepositoryImpl(application)
    private val deleteCardUseCase = DeleteCardUseCase(repository)


    val cardListLD = repository.getCardList()


    fun deleteCardItem(cardItem: CardItem) {
        viewModelScope.launch {
            deleteCardUseCase.deleteCard(cardItem)
        }
    }

}