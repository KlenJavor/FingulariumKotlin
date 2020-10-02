package com.example.fingularium

import com.example.fingularium.model.Word
import retrofit2.Response
import retrofit2.http.*

interface VocabularyApi {
    //~sergeyu/words.json
    //~patricsu/Simple100
    @GET("~patricsu/Full_unform_200")
    suspend fun getCustomPosts(    ): Response<List<List<Word>>>



}