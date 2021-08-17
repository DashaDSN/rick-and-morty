package com.andersen.rickandmorty.ui.episodes

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<Int>,
    val url: String,
    val created: String)
