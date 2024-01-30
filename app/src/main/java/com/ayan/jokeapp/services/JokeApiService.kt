package com.ayan.jokeapp.services

import com.ayan.jokeapp.models.Joke
import retrofit2.Response
import retrofit2.http.GET

interface JokeApiService {
    @GET("joke/Programming?type=single")
    suspend fun getProgrammingJoke(): Response<Joke>
}
