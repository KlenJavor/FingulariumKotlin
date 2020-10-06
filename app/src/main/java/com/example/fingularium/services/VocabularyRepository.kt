package com.example.fingularium.services

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import com.example.fingularium.model.Word
import com.example.fingularium.services.VocabularyItem
import retrofit2.Response

class vocabularyRepository {

    suspend fun getVocabulary(): Response<List<List<Word>>> {
        return VocabularyItem.api.getCustomPosts()
    }
}