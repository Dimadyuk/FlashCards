package com.example.flashcards.presentation.main_activity.rv_classes

import androidx.recyclerview.widget.DiffUtil
import com.example.flashcards.domain.CardItem

class CardListDiffCallback(
    private val oldList: List<CardItem>,
    private val newList: List<CardItem>
): DiffUtil.Callback()  {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCard = oldList[oldItemPosition]
        val newCard = newList[newItemPosition]

        return oldCard.id == newCard.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCard = oldList[oldItemPosition]
        val newCard = newList[newItemPosition]

        return oldCard == newCard
    }

}