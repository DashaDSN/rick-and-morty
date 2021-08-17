package com.andersen.rickandmorty.ui.locations

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Int>,
    val url: String,
    val created: String)
