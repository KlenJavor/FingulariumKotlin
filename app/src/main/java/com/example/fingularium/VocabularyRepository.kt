package com.example.fingularium

import com.example.fingularium.model.Word
import retrofit2.Response

class VocabularyRepository {

    suspend fun getCustomPosts(): Response<List<List<Word>>> {
        return VocabularyItem.api.getCustomPosts()
    }
}