package com.example.fingularium

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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