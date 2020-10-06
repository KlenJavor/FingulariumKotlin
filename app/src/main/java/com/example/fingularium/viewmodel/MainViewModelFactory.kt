package com.example.fingularium.viewmodel

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fingularium.services.vocabularyRepository

class MainViewModelFactory(private val repository: vocabularyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VocabularyViewModel(repository) as T
    }
}