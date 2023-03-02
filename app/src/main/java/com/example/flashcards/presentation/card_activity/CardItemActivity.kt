package com.example.flashcards.presentation.card_activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.data.network.api.ApiFactory
import com.example.flashcards.data.network.api.ApiService
import com.example.flashcards.data.network.pojo.ResponseData
import com.example.flashcards.databinding.ActivityCardItemBinding
import com.example.flashcards.domain.CardItem
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class CardItemActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    private val compositeDisposable = CompositeDisposable()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val binding by lazy {
        ActivityCardItemBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: CardItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CardItemViewModel::class.java]
        val card = intent.extras?.get("card") as CardItem?
        val mod = intent.extras?.get("mod") as String
        if (card != null) {
            binding.etWord.setText(card.word)
            binding.etTranslation.setText(card.translation)
        }
        binding.btnSave.setOnClickListener {

            if (mod == MODE_ADD) {
                val newCard =
                    CardItem(binding.etWord.text.toString(), binding.etTranslation.text.toString())
                viewModel.addCardItem(newCard)
            } else if (card != null && mod == MODE_EDIT) {
                val editItem =
                    card.copy(
                        word = binding.etWord.text.toString(),
                        translation = binding.etTranslation.text.toString()
                    )
                viewModel.editCardItem(editItem)
            }
            finish()
        }

        binding.etWord.setOnEditorActionListener(this)
        binding.etTranslation.setOnEditorActionListener(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        coroutineScope.cancel()
    }

    private fun findTranslation(langPair: String, word: String) {
        coroutineScope.launch {
            val wordInBase = viewModel.getCardItemByWord(word)

            if (wordInBase == null) {
                val disposable = ApiFactory.apiService.getTranslationDatum(
                    q = word,
                    langpair = langPair
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val data = Gson().fromJson(it.cardInfoJsonObject, ResponseData::class.java)
                        val translation = data.translatedText ?: ""
                        when (langPair) {
                            ApiService.EN_RU -> {
                                binding.etTranslation.setText(translation)
                                if (translation == "") {
                                    binding.etTranslation.hint = "слово не найдено!"
                                }
                            }
                            ApiService.RU_EN -> {
                                binding.etWord.setText(translation)
                                if (translation == "") {
                                    binding.etWord.hint = "слово не найдено!"
                                }
                            }
                            else -> {
                                binding.etTranslation.setText(translation)
                                if (translation == "") {
                                    binding.etTranslation.hint = "слово не найдено!"
                                }
                            }
                        }
                        Log.d("TEST", "$data  ---  $it")
                    }, {
                        Log.d("TEST", it.message.toString())
                    })
                compositeDisposable.add(disposable)
            } else {
                binding.etTranslation.setText(wordInBase.translation)
            }

        }


    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        var word = ""
        val langPair = when (v) {
            binding.etWord -> {
                word = binding.etWord.text.toString()
                ApiService.EN_RU
            }
            binding.etTranslation -> {
                word = binding.etTranslation.text.toString()
                ApiService.RU_EN
            }
            else -> {
                word = binding.etWord.text.toString()
                ApiService.EN_RU
            }
        }
        findTranslation(langPair, word)
        return false
    }

    companion object {
        const val MODE_EDIT = "mod_edit"
        const val MODE_ADD = "mod_add"
    }
}