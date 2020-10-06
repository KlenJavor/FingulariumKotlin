package com.example.fingularium.services;

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import com.example.fingularium.model.Word
import retrofit2.Retrofit;
import retrofit2.http.GET;
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @VocabularyFetch implement the retrofit functionality to get a json file from an online source
 */

class Constants {
    companion object{
        const val BASE_URL = "https://users.metropolia.fi/"
    }
}

private const val BASE_URL = "https://users.metropolia.fi/~patricsu/Full_unform_200.json"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface VocabularyService {
    @GET("Full_unform_200")
     fun getWords(): Deferred<List<Word>>
}

object VocabularyApi {
    val retrofitService: VocabularyService by lazy {
        retrofit.create(VocabularyService::class.java)
    }
}

