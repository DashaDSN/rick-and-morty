package com.andersen.rickandmorty.model.remote

import com.andersen.rickandmorty.model.CharacterDetail

data class CharacterRetrofit(
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
    val created: String) {
    
    data class ShortLocation (
        val name: String,
        val url: String)

    fun toCharacter() = CharacterDetail(
        this.id,
        this.name,
        this.status,
        this.species,
        this.type,
        this.gender,
        this.origin.name,
        this.location.name,
        this.image,
        this.episodes,
        this.url
    )
}
