package com.example.flashcards.presentation.main_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcards.R
import com.example.flashcards.databinding.ActivityMainBinding
import com.example.flashcards.domain.CardItem
import com.example.flashcards.presentation.card_activity.CardItemActivity
import com.example.flashcards.presentation.examination_activity.ExaminationActivity
import com.example.flashcards.presentation.examination_activity.ExaminationFragment
import com.example.flashcards.presentation.main_activity.rv_classes.CardAdapter
import com.example.flashcards.presentation.main_activity.rv_classes.CardTouchHelper

class MainActivity : AppCompatActivity(), CardAdapter.Listener {

    private lateinit var viewModel: MainViewModel
    private lateinit var cardAdapter: CardAdapter

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.cardListLD.observe(this) {
            cardAdapter.cardList = it
        }

        binding.buttonAddCardItem.setOnClickListener {
            val intent = Intent(this, CardItemActivity::class.java)
            intent.putExtra("mod", CardItemActivity.MODE_ADD)
            startActivity(intent)
        }

        binding.buttonStartTest.setOnClickListener {
            val intent = Intent(this, ExaminationActivity::class.java)
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, ExaminationFragment.newInstance())
//                .commit()
            startActivity(intent)
        }

        val callback = CardTouchHelper(cardAdapter, viewModel)
        val touchHelper = ItemTouchHelper(callback)

        touchHelper.attachToRecyclerView(binding.rvCardList)


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initRecyclerView() {

        cardAdapter = CardAdapter(this)

        with(binding.rvCardList) {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onClick(cardItem: CardItem, position: Int) {
        val intent = Intent(this, CardItemActivity::class.java)
        intent.putExtra("card", cardItem)
        intent.putExtra("mod", CardItemActivity.MODE_EDIT)
        startActivity(intent)
    }


}