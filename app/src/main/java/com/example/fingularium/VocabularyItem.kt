package com.example.fingularium

import com.example.fingularium.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VocabularyItem {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api: VocabularyApi by lazy {
        retrofit.create(VocabularyApi::class.java)
    }
}