package com.example.flashcards.presentation.examination_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentExaminationBinding
import com.example.flashcards.domain.CardItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Collections.shuffle


class ExaminationFragment : Fragment() {

    private lateinit var viewModel: ExaminationViewModel
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val binding by lazy {
        FragmentExaminationBinding.inflate(layoutInflater)
    }

    var list = listOf<CardItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ExaminationViewModel::class.java]

        var id = 0

        uiScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                list = viewModel.loadList()
                shuffle(list)
            }.join()

            binding.tvWord.text = list[id].word
            binding.tvTranslation.text = ""
        }

        binding.btnCheck.setOnClickListener {
            if (id <= list.size - 1) {

                when (binding.btnCheck.text) {
                    getString(R.string.next) -> {
                        id++
                        binding.tvWord.text = list[id].word
                        binding.tvTranslation.text = ""
                        binding.btnCheck.setText(R.string.check_answer)
                    }
                    getString(R.string.check_answer) -> {
                        binding.tvTranslation.text = list[id].translation
                        if (id == list.size - 1) {
                            binding.btnCheck.text = getString(R.string.finish)
                        } else binding.btnCheck.text = getString(R.string.next)
                    }
                    else -> activity?.finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }

    companion object {


        fun newInstance() =
            ExaminationFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}