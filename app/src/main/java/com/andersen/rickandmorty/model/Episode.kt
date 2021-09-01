package com.andersen.rickandmorty.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey val id: Int,
    val name: String,
    val episode: String,
    val air_date: String)
