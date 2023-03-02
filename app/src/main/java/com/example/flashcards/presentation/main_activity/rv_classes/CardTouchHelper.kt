package com.example.flashcards.presentation.main_activity.rv_classes

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.presentation.main_activity.MainViewModel

class CardTouchHelper(
    private var cardAdapter: CardAdapter,
    private var viewModel: MainViewModel
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
       // cardAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val item = cardAdapter.cardList[viewHolder.adapterPosition]
        viewModel.deleteCardItem(item)
    }
}