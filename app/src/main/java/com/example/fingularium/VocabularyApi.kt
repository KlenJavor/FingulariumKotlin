package com.example.fingularium

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import com.example.fingularium.model.Word
import retrofit2.Response
import retrofit2.http.*

interface VocabularyApi {
    @GET("~patricsu/Full_unform_200")
    suspend fun getCustomPosts(): Response<List<List<Word>>>
}