package com.ayan.jokeapp.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://v2.jokeapi.dev/"

    fun getInstance(): JokeApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeApiService::class.java)
    }
}