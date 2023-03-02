package com.example.flashcards.presentation.examination_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.R
import com.example.flashcards.databinding.ActivityExaminationBinding
import com.example.flashcards.domain.CardItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections.shuffle

class ExaminationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityExaminationBinding.inflate(layoutInflater)
    }



    private lateinit var viewModel: ExaminationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ExaminationFragment.newInstance())
            .commit()

        viewModel = ViewModelProvider(this)[ExaminationViewModel::class.java]

    }
}