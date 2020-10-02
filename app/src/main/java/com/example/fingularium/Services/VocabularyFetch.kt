package com.example.fingularium.Services;

import com.example.fingularium.Word
import retrofit2.Retrofit;
import retrofit2.http.GET;
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://users.metropolia.fi/~patricsu/Simple.json"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface VocabularyService {
    @GET("Simple")
     fun getWords(): Deferred<List<Word>>
}


object VocabularyApi {
    val retrofitService: VocabularyService by lazy {
        retrofit.create(VocabularyService::class.java)
    }
}

