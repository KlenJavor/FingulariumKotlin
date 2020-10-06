package com.example.fingularium

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import com.example.fingularium.model.Word
import retrofit2.Response

class VocabularyRepository {

    suspend fun getCustomPosts(): Response<List<List<Word>>> {
        return VocabularyItem.api.getCustomPosts()
    }
}