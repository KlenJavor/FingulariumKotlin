package com.example.fingularium.viewmodel

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fingularium.services.vocabularyRepository
import com.example.fingularium.model.Word
import kotlinx.coroutines.launch
import retrofit2.Response

// Contains mutable live data object which is used in the VocabularyActivity

class VocabularyViewModel(private val repository: vocabularyRepository): ViewModel() {

    var myVocabulary: MutableLiveData<Response<List<List<Word>>>> = MutableLiveData()

    fun getVocabulary() {
        viewModelScope.launch {
            val response = repository.getVocabulary()
            myVocabulary.value = response
        }
    }
}