package com.ayan.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.ayan.jokeapp.databinding.ActivityMainBinding
import com.ayan.jokeapp.utils.NetworkUtils
import com.ayan.jokeapp.viewmodels.JokesViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var jokesViewModel: JokesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        jokesViewModel = ViewModelProvider(this)[JokesViewModel::class.java]
        setContentView(binding.root)
        setUpObservers()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnFetch.setOnClickListener {
            GlobalScope.launch {
                jokesViewModel.fetchJokes()
            }
        }
    }

    private fun setUpObservers() {
        jokesViewModel.jokeData.observe(this) { joke ->
            //update the ui
            binding.apply {
                tvJoke.text = joke.joke
                tvMemeLength.text = getString(R.string.length_count, joke.joke.length.toString())
                tvMemeLength.isVisible = joke.joke.length > 80
                tvWordCount.text = getString(R.string.words_counts, jokesViewModel.countWords(joke.joke).toString())
                btnFetch.text = getString(R.string.update_joke)
                tvDataFromText.isVisible = joke.isFromLocal
                jokesViewModel.saveJokeToLocal(joke)
            }
        }

        jokesViewModel.toastString.observe(this) {
            Toast.makeText(applicationContext, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}