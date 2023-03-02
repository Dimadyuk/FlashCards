package com.example.flashcards.presentation.main_activity.rv_classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.domain.CardItem
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter(private val listener: Listener) : RecyclerView.Adapter<CardViewHolder>() {

    var cardList = listOf<CardItem>()
        set(value) {
            val callback = CardListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val cardItem = cardList[position]

        holder.itemView.tv_word.text = cardItem.word
        holder.itemView.tv_translation.text = cardItem.translation

        holder.itemView.setOnClickListener {
            listener.onClick(cardItem, position)
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    interface Listener {
        fun onClick(cardItem: CardItem, position: Int)
    }
}

