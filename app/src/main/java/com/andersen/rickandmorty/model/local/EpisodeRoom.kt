package com.andersen.rickandmorty.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

data class EpisodeRoom(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String)
