package com.andersen.rickandmorty.ui.characters

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Int,
    val location: Int,
    val image: String,
    val episodes: List<Int>,
    val url: String,
    val created: String)
