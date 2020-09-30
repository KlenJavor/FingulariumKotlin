package com.example.a6_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VocabularyViewModel : ViewModel() {

    private val _response = MutableLiveData<List<Word>>()


    val response: LiveData<List<Word>>
        get() = _response

    init {
        getWords()
    }

    private fun getWords() {
        VocabularyApi.retrofitService.getWords().enqueue(object: Callback<List<Word>> {
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                _response.value = response.body()
            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
            }
        })

    }
}