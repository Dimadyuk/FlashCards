package com.example.flashcards.data

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.flashcards.data.database.AppDatabase
import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository

class CardListRepositoryImpl(
    application: Application
) : CardListRepository {

    private val cardListDao = AppDatabase.getInstance(application).cardListDao()
    private val mapper = CardMapper()

    override suspend fun addCardItem(cardItem: CardItem) {
        cardListDao.addCardItem(mapper.mapEntityToDbModel(cardItem))
    }

    override suspend fun deleteCardItem(cardItem: CardItem) {
        cardListDao.deleteCardItem(cardItem.id)
    }

    override suspend fun editCardItem(cardItem: CardItem) {
        cardListDao.addCardItem(mapper.mapEntityToDbModel(cardItem))
    }

    override suspend fun getCardItem(id: Int): CardItem {
        val dbModel = cardListDao.getCardItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun getCardItemByWord(word: String): CardItem? {
        val dbModel = cardListDao.getCardItemByWord(word)
        if (dbModel != null) {
            return mapper.mapDbModelToEntity(dbModel)
        }
        return null
    }

    override fun getCardList(): LiveData<List<CardItem>> {
        return Transformations.map(cardListDao.getCardList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun getDisplacedCardList(): List<CardItem> {

        return mapper.mapListDbModelToListEntity(cardListDao.getCardListDb())

    }


}