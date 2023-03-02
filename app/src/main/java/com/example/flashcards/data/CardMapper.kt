package com.example.flashcards.data

import androidx.lifecycle.LiveData
import com.example.flashcards.data.database.CardItemDbModel
import com.example.flashcards.domain.CardItem

class CardMapper {

    fun mapEntityToDbModel(cardItem: CardItem) =
        CardItemDbModel(
            cardItem.word,
            cardItem.translation,
            cardItem.id
        )

    fun mapDbModelToEntity(cardItemDbModel: CardItemDbModel) = CardItem(
        id = cardItemDbModel.id,
        word = cardItemDbModel.word,
        translation = cardItemDbModel.translation
    )

    fun mapListDbModelToListEntity(list: List<CardItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}