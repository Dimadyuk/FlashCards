package com.example.flashcards.presentation.examination_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.flashcards.data.CardListRepositoryImpl
import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.use_cases.GetDisplacedCardListUseCase

class ExaminationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CardListRepositoryImpl(application)
    private val getDisplacedCardListUseCase = GetDisplacedCardListUseCase(repository)

    suspend fun loadList(): List<CardItem> {
        return getDisplacedCardListUseCase.getDisplacedCardList()
    }

}