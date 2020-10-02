package com.example.fingularium.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fingularium.VocabularyRepository
import com.example.fingularium.model.Word
import kotlinx.coroutines.launch
import retrofit2.Response

class VocabularyViewModel(private val repository: VocabularyRepository): ViewModel() {


    var myCustomPosts: MutableLiveData<Response<List<List<Word>>>> = MutableLiveData()



    fun getCustomPosts() {
        viewModelScope.launch {
            val response = repository.getCustomPosts()
            myCustomPosts.value = response
        }
    }



}