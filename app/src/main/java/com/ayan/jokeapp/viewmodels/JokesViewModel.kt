package com.ayan.jokeapp.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayan.jokeapp.models.Joke
import com.ayan.jokeapp.services.RetrofitHelper
import com.ayan.jokeapp.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

class JokesViewModel(val context: Application): AndroidViewModel(context) {

    private val _data = MutableLiveData<Joke>()
    private val errorString = MutableLiveData<String>()
    private val jokeApi = RetrofitHelper.getInstance()
    private var response: Response<Joke>? = null

    private val SHARED_PREF_NAME = "joke_prefs"
    private val JOKE_KEY = "stored_joke"

    val jokeData: LiveData<Joke>
        get() = _data
    val toastString: LiveData<String>
        get() = errorString

    suspend fun fetchJokes() {
        try {
            response = jokeApi.getProgrammingJoke()
            if(response!!.body() != null) {
                _data.postValue(response!!.body())
            } else {
                if (NetworkUtils.isInternetAvailable(context)) {
                    errorString.postValue("Response body is null!!")
                } else fetchJokeFromLocal()
            }
        } catch (e: Exception){
            if (NetworkUtils.isInternetAvailable(context)) {
                errorString.postValue("error: ${e.message}")
            } else fetchJokeFromLocal()
        }
    }

    fun countWords(inputString: String): Int {
        val words = inputString.split("\\s+".toRegex())
        return words.size
    }

    private fun fetchJokeFromLocal() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val storedJokeJson = sharedPreferences.getString(JOKE_KEY, null)

        if (!storedJokeJson.isNullOrBlank()) {
            // If a joke is stored locally, deserialize the JSON string and update _data
            val gson = Gson()
            val type = object : TypeToken<Joke>() {}.type
            val storedJoke = gson.fromJson<Joke>(storedJokeJson, type)

            _data.postValue(storedJoke.apply { isFromLocal = true })
        }
    }

    fun saveJokeToLocal(joke: Joke) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val jokeJson = gson.toJson(joke)

        editor.putString(JOKE_KEY, jokeJson)
        editor.apply()
    }

}
