package com.ayan.jokeapp.services

object JokeRepository {
    //api
    private val jokeApi = RetrofitHelper.getInstance()

    suspend fun getData() = jokeApi.getProgrammingJoke()
}