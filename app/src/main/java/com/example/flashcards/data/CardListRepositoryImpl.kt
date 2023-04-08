package com.example.flashcards.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.flashcards.data.database.AppDatabase
import com.example.flashcards.data.network.api.ApiFactory
import com.example.flashcards.data.network.api.ApiService
import com.example.flashcards.data.network.pojo.ResponseData
import com.example.flashcards.domain.CardItem
import com.example.flashcards.domain.CardListRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    override suspend fun getTranslation(langPair: String, word: String): Pair<String, String> {

        val wordInBase = if (langPair == ApiService.EN_RU) {
            cardListDao.getCardItemByWord(word)
        } else {
            cardListDao.getCardItemByTranslation(word)
        }
        var wordEN = wordInBase?.word ?: ""
        var translationRU = wordInBase?.translation ?: ""

        if (wordInBase == null) {
            withContext(Dispatchers.IO) {
                val response = ApiFactory.apiService.getTranslationDatum(
                    q = word,
                    langpair = langPair
                )
                val result = Gson().fromJson(response.cardInfoJsonObject, ResponseData::class.java)
                translationRU = result.translatedText ?: ""
            }

        }
        val pair = if (langPair == ApiService.EN_RU) {
            Pair(wordEN, translationRU)
        } else {
            Pair(translationRU, wordEN)
        }
        return pair
    }

}