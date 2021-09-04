package com.andersen.rickandmorty.model

// model contains list of T in results field
data class ServerResponse<T> (
    val info: Info,
    val results: List<T>
) {
    data class Info (
        val count: Int,
        val pages: Int,
        val next: String,
        val prev: String
    )
}
