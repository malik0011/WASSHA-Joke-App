package com.ayan.jokeapp.models

import java.io.Serializable

data class Joke(
    val category: String,
    val error: Boolean,
    val flags: Flags,
    val id: Int,
    val joke: String,
    val lang: String,
    val safe: Boolean,
    val type: String,
    var isFromLocal: Boolean = false
) : Serializable