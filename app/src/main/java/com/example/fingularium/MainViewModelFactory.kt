package com.example.retrofittest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fingularium.VocabularyViewModel
import com.example.fingularium.VocabularyRepository

class MainViewModelFactory(private val repository: VocabularyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VocabularyViewModel(repository) as T
    }
}