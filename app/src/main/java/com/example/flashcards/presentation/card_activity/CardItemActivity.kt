package com.example.flashcards.presentation.card_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.flashcards.R
import com.example.flashcards.data.network.api.ApiFactory
import com.example.flashcards.data.network.api.ApiService
import com.example.flashcards.data.network.pojo.ResponseData
import com.example.flashcards.databinding.ActivityCardItemBinding
import com.example.flashcards.domain.CardItem
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_card_item.*
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

        val (card, mod) = parseIntent()
        setOnClickListeners(mod, card)
        addTextChangeListeners()
        observeViewModel()
    }


    private fun setOnClickListeners(
        mod: String?,
        card: CardItem?
    ) {
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

    private fun parseIntent(): Pair<CardItem?, String?> {
        val card = intent.extras?.get(EXTRA_CARD_ITEM) as CardItem?
        val mod = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (card != null) {
            binding.etWord.setText(card.word)
            binding.etTranslation.setText(card.translation)
        }
        return Pair(card, mod)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        coroutineScope.cancel()
    }

    private fun findTranslation(langPair: String, word: String) {
        lifecycleScope.launch {

            val pair = withContext(Dispatchers.IO) {
                viewModel.getTranslation(langPair, word)
            }
            val wordEN = pair.first
            val translationRU = pair.second
            setErrorOrTranslation(langPair, wordEN, translationRU)
        }


    }

    private fun addTextChangeListeners() {
        binding.etWord.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputWord()
                viewModel.resetErrorInputTranslation()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.etTranslation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputTranslation()
                viewModel.resetErrorInputWord()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun observeViewModel() {
        viewModel.errorInputWord.observe(this) {
            val massage = if (it) {
                getString(R.string.word_not_found)
            } else {
                null
            }
            binding.tilWord.error = massage
        }

        viewModel.errorInputTranslation.observe(this) {
            val massage = if (it) {
                getString(R.string.word_not_found)
            } else {
                null
            }
            binding.tilTranslation.error = massage
        }
    }

    private fun setErrorOrTranslation(langPair: String, wordEN: String, translationRU: String) {
        when (langPair) {
            ApiService.EN_RU -> {
                binding.etTranslation.setText(translationRU)
                if (translationRU == "") {
                    binding.tilWord.error = getString(R.string.word_not_found)
                }
            }
            ApiService.RU_EN -> {
                binding.etWord.setText(wordEN)
                if (wordEN == "") {
                    binding.tilTranslation.error = getString(R.string.word_not_found)
                }
            }
            else -> {
                binding.etTranslation.setText(translationRU)
                if (translationRU == "") {
                    binding.tilWord.error = getString(R.string.word_not_found)
                }
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
        viewModel.resetErrorInputWord()
        viewModel.resetErrorInputTranslation()
        findTranslation(langPair, word)
        return false
    }

    companion object {
        private const val MODE_EDIT = "mod_edit"
        private const val MODE_ADD = "mod_add"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_CARD_ITEM = "extra_shop_item"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, CardItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, cardItem: CardItem): Intent {
            val intent = Intent(context, CardItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_CARD_ITEM, cardItem)
            return intent
        }
    }
}