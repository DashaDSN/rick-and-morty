package com.andersen.rickandmorty.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: ShortLocation,
    val location: ShortLocation,
    val image: String,
    val episodes: List<String>,
    val url: String,
    val created: String)

data class ShortLocation (
    val name: String,
    val url: String)