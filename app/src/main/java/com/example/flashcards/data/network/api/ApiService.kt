package com.example.flashcards.data.network.api

import com.example.flashcards.data.network.pojo.ResponseDataJson
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("get?")
    fun getTranslationDatum(
        @Query(QUERY_PARAM_WORD) q: String = "Hello world",
        @Query(QUERY_PARAM_ENABLED_MACHINE_TRANSLATION) mt: String = "",
        @Query(QUERY_PARAM_KEY) key: String = KEY,
        @Query(QUERY_PARAM_LAND_PAIR) langpair: String = EN_RU,
    ): Single<ResponseDataJson>

    companion object {

        private const val QUERY_PARAM_WORD = "q"
        private const val QUERY_PARAM_ENABLED_MACHINE_TRANSLATION = "mt"
        private const val QUERY_PARAM_KEY = "key"
        private const val QUERY_PARAM_LAND_PAIR = "langpair"

        const val EN_RU = "en|ru"
        const val RU_EN = "ru|en"
        private const val KEY = "ac9c43b11e3af3a60f32"
    }
}