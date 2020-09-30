package com.example.a6_1;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET;
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://users.metropolia.fi/~patricsu"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface VocabularyService {
    @GET("Finnish_words.json")
    fun getWords():
            Call<List<Word>>
}


object VocabularyApi {
    val retrofitService: VocabularyService by lazy {
        retrofit.create(VocabularyService::class.java)
    }
}
