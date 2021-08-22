package com.andersen.rickandmorty.model

data class LocationRoom(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String)
