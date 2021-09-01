package com.andersen.rickandmorty.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

data class LocationRoom(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String)
