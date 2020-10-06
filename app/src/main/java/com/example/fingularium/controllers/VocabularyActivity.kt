package com.example.fingularium.controllers

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fingularium.R
import com.example.fingularium.services.vocabularyRepository
import com.example.fingularium.adapters.VocabularyAdapter
import com.example.fingularium.viewmodel.MainViewModelFactory
import com.example.fingularium.viewmodel.VocabularyViewModel
import kotlinx.android.synthetic.main.activity_vocabulary.*

/**
 * @VocabularyActivity displays the full vocabulary in a recycler view
 */

class VocabularyActivity : AppCompatActivity() {
    private lateinit var viewModel: VocabularyViewModel
    private val myAdapter by lazy { VocabularyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary)
        setupRecyclerview()
        val repository = vocabularyRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VocabularyViewModel::class.java)
        viewModel.getVocabulary()
        viewModel.myVocabulary.observe(this, { response ->
            if (response.isSuccessful) {
                response.body()?.let { myAdapter.setData(it) }  //This is the actual vocabulary
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerview() {
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}