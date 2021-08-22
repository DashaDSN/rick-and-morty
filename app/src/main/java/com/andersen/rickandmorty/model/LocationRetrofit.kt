package com.andersen.rickandmorty.model

data class LocationRetrofit(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String)
